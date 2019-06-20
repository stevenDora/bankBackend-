package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.components.redis.impl.RedisDistributedLock;
import cn.stylefeng.guns.modular.system.constant.Constant;
import cn.stylefeng.guns.modular.system.dao.*;
import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.*;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.modular.system.constant.Constant.COMPANY_CASH_OWNER;
import static cn.stylefeng.guns.modular.system.constant.Constant.Lock.COMPANY_LOCK_PREFIX;
import static cn.stylefeng.guns.modular.system.constant.Constant.Lock.SCALPER_LOCK_PREFIX;
import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.SCALPER_CASH_OWNER;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-13
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements ITradeService {


    private static Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    @Resource
    private RedisDistributedLock redisDistributedLock;

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private IFlowDataService flowDataService;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private AssignOrderService assignOrderService;

    @Autowired
    private IScalperService scalperService;

    @Autowired
    private CompanyService companyService;

    @Override
    public Trade selectTradeByOrderNo(String orderNo) {
        if(StringUtils.isEmpty(orderNo)){
            return null;
        }
        return tradeMapper.selectTradeByOrderNo(orderNo);
    }

    @Override
    @Transactional
    public void createOrder(String orderNo) {
        String orderStr = redisDao.get(orderNo);
        if(StringUtils.isEmpty(orderStr)){
            throw new ServiceException(UN_KNOW_ERROR);
        }
        Trade trade = JSONObject.parseObject(orderStr, Trade.class);
        trade.setOrderStatus(ORDER_STATUS_PROCESS);
        AccountSelectRsp rsp = assignOrderService.accountSelect(trade);

        if(!StringUtils.isEmpty(rsp)&&
                !StringUtils.isEmpty(rsp.getAccount_info())){
            trade.setRemark(rsp.getRemark());
            trade.setAccountId(rsp.getAccount_id());
            trade.setAccountInfo(rsp.getAccount_info());
            trade.setScalperId(rsp.getScalper_id());
            logger.info("assign order failed,empty orderNo:{},companyOrderNo:{}",
                    orderNo,trade.getCompanyOrderNo());
        }
        tradeMapper.insert(trade);
        redisDao.set(orderNo, JSONObject.toJSONString(trade));
    }

    @Override
    public List<Trade> findTradeInfoWithInvalidOverTime(Integer num) {
        return tradeMapper.findTradeInfoWithInvalidOverTime(num);
    }


    @Override
    @Transactional
    public void handleSucOrder(Integer flowNo){
        FlowData flowData  = flowDataService.getFlowDataById(flowNo);
        if(StringUtils.isEmpty(flowData)){
            throw new ServiceException(FLOW_NOT_EXIST);
        }
        Trade trade = tradeMapper.findTradeByAmountAndRemark(flowData);

        Integer row = flowDataService.updateFlowData(flowNo,trade.getOrderNo());
        if(row == 0){
            logger.info("可能是多线程并发,flowNo = {} 已经被匹配!!!",flowNo);
            return;
        }
        row = tradeMapper.matchOrder(trade.getOrderNo(),ORDER_STATUS_SUCCESS);
        if(row == 0){
            logger.info("可能是多线程并发,订单已经被匹配或者订单已经失效!!! " +
                            "orderNo = {} ,orderInfo = {} ",trade.getOrderNo(),trade.toString());
            return;
        }
        logger.info("订单匹配成功,打上做账标记!!! " +
                "orderNo = {} ,orderInfo = {} ",trade.getOrderNo(),trade.toString());
    }


    @Transactional
    @Override
    public void invalidOrder(String orderNo){
        Integer row = tradeMapper.matchOrder(orderNo,ORDER_STATUS_OVERDUE);
        if(row == 0){
            logger.info("invalidOrder失败 可能是多线程并发,订单已经被匹配或者订单已经失效!!! " +
                    "orderNo = {} ,orderInfo = {} ",orderNo);
            return;
        }
    }

    @Override
    public List<Trade> findNeedAcountChangeOrders(Integer num) {
        return tradeMapper.findNeedAcountChangeOrders(num);
    }

    @Transactional
    @Override
    public void handleAccountChange(Trade trade) {
        if(StringUtils.isEmpty(trade)){
            return;
        }
        Integer row = tradeMapper.execAccountChange(trade.getOrderNo());
        if(row == 0){
            logger.info("orderNo:{} has finished account",trade.getOrderNo());
            return;
        }
        if(trade.getOrderStatus() == ORDER_STATUS_SUCCESS){
            logger.info("orderNo:{} successfully，execute normal cash flow!!",trade.getOrderNo());
        }
        else if(trade.getOrderStatus() == ORDER_STATUS_OVERDUE){
            logger.info("orderNo:{} overdue，execute rollback cash flow!!",trade.getOrderNo());
        }
        else {
            throw new ServiceException(UN_KNOW_ERROR);
        }
        saveCashFlow(trade,COMPANY_CASH_OWNER,trade.getCompanyNo()+"",COMPANY_LOCK_PREFIX);
        saveCashFlow(trade,SCALPER_CASH_OWNER,trade.getScalperId(),SCALPER_LOCK_PREFIX);
    }


    @Override
    @Transactional
    public void saveCashFlow(Trade trade,String cashOwner,String lockId,String lockPrefix){
        boolean locked = true;
        String locker = redisDao.getKey(lockPrefix, lockId);
        try{
            if (redisDistributedLock.lock(locker)) {
                if(cashOwner.equals(COMPANY_CASH_OWNER)){
                    companyService.saveCompanyCashFlow(trade);
                }
                else {
                    scalperService.saveScalperCashFlow(trade);
                }

            }
            else {
                locked = false;
                logger.info("该商户已被其他进程锁定，请等待操作");
                throw new ServiceException(LOCK_TIMEOUT);
            }
        }catch (Exception e){
            throw new ServiceException(UN_KNOW_ERROR);
        }finally {
            logger.info("釋放分布式锁");
            redisDistributedLock.unlock(locker, locked);
        }
    }

}
