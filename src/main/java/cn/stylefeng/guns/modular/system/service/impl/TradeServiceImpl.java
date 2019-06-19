package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;
import cn.stylefeng.guns.modular.system.model.FlowData;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.dao.TradeMapper;
import cn.stylefeng.guns.modular.system.service.AssignOrderService;
import cn.stylefeng.guns.modular.system.service.IFlowDataService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.ORDER_STATUS_PROCESS;
import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.ORDER_STATUS_SUCCESS;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.FLOW_NOT_EXIST;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.UN_KNOW_ERROR;

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



    @Autowired
    private TradeMapper tradeMapper;
    
    @Autowired
    private IFlowDataService flowDataService;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private AssignOrderService assignOrderService;

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
        AccountSelectRsp rsp = assignOrderService.accountSelect(orderNo,
                trade.getChannel(),
                trade.getApplyAmount());

        trade.setRemark(rsp.getRemark());
        trade.setAccountId(rsp.getAccount_id());
        trade.setAccountInfo(rsp.getAccount_info());
        trade.setOrderStatus(ORDER_STATUS_PROCESS);
        tradeMapper.insert(trade);
        redisDao.set(orderNo, JSONObject.toJSONString(trade));
    }

    @Override
    public List<Trade> findTradeInfoWithInvalidOverTime() {
        return tradeMapper.findTradeInfoWithInvalidOverTime();
    }


    @Override
    @Transactional
    public void matchOrder(Integer flowNo){
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
        Integer row = tradeMapper.matchOrder(orderNo,ORDER_STATUS_SUCCESS);
        if(row == 0){
            logger.info("invalidOrder失败 可能是多线程并发,订单已经被匹配或者订单已经失效!!! " +
                    "orderNo = {} ,orderInfo = {} ",orderNo);
            return;
        }
    }
    
}
