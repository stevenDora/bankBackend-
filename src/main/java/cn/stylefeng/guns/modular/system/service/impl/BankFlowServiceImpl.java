package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.components.redis.impl.RedisDistributedLock;
import cn.stylefeng.guns.modular.system.constant.Constant;
import cn.stylefeng.guns.modular.system.dao.BankFlowMapper;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import cn.stylefeng.guns.modular.system.service.IBankFlowService;
import cn.stylefeng.guns.modular.system.service.IBankMgrParmService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.guns.modular.system.utils.http.HttpClient;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import cn.stylefeng.guns.modular.system.model.BankFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import java.util.List;

import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.BANK_FLOW_DONE;
import static cn.stylefeng.guns.modular.system.constant.Constant.BANK_FLOW_PROCESSING;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
@Service
public class BankFlowServiceImpl extends ServiceImpl<BankFlowMapper, BankFlow> implements IBankFlowService {

    private static Logger logger = LoggerFactory.getLogger(BankFlowServiceImpl.class);


    @Resource
    private IBankMgrParmService bankMgrParmService;

    @Autowired
    private BankFlowMapper bankFlowMapper;

    @Resource
    private RedisDao redisDao;

    @Resource
    private RedisDistributedLock redisDistributedLock;

    @Transactional(rollbackFor = Exception.class)
    public void processCashflows(){
        boolean locked_cashflow = true;
        String lock = redisDao.getKey(Constant.Lock.CASH_FLOW_LOCK_PREFIX, "");
        try {
            if (redisDistributedLock.lock(lock)){
                Integer batchNum = bankMgrParmService.getBatchNum();
                if(StringUtils.isEmpty(batchNum)){
                    logger.info("processCashflow failed ,batchNum = 0 or null");
                    return;
                }
                Integer last_cursor = bankMgrParmService.getLastCursor();
                if(StringUtils.isEmpty(last_cursor)){
                    logger.info("processCashflow failed ,no last_cursor");
                    return;
                }
                int count = beforeSend(batchNum, last_cursor);
                doSend();
                afterSend();
                bankMgrParmService.updateLastCursor(last_cursor + count);
            }else {
                locked_cashflow = false;
                logger.info("该操作已被其他进程锁定，请等待操作");
                throw new ServiceException(BANK_CARD_LOCK_FAILED);
            }
        }catch (Exception e){
            throw new ServiceException(BANK_CARD_CASH_FLOW_FAILED);
        }finally {
            /*釋放鎖*/
            logger.info("釋放分布式锁");
            redisDistributedLock.unlock(lock, locked_cashflow);
        }


    }

    @Transactional(rollbackFor = Exception.class)
    private int beforeSend(Integer batchNum, Integer last_cursor) {
        int count = 0;
        for (;count < batchNum;count ++){
            Boolean success = handleBankFlow(last_cursor + count, BANK_FLOW_PROCESSING);
            if(!success){
                logger.info("last_cursor{} has been processed skip it",last_cursor);
                continue;
            }
        }
        return count;
    }

    private void doSend(){
        List<BankFlow> flows = bankFlowMapper.getFlowsByStatus(BANK_FLOW_PROCESSING);
        String stream = JSON.toJSONString(flows);
        //发送
        String url = "";
        String responseStr = HttpClient.sendPost(url +"",stream);
        logger.info("push cashflow 反馈{}", responseStr);
        ResponseResult response = JSONObject.parseObject(responseStr, ResponseResult.class);
        if (response.getCode() != 200) {
            logger.error("流水转发失败!!!! error->  "+response);
            throw new ServiceException(BANK_CARD_CASH_FLOW_FORWARD_FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void afterSend(){
        bankFlowMapper.handleBankFlows(BANK_FLOW_DONE);
    }


    Boolean handleBankFlow(Integer position,Byte status){
        Integer row = bankFlowMapper.handleBankFlow(position,status);
        return row>0;
    }

}
