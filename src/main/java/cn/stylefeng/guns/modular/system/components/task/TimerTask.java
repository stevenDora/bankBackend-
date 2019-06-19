package cn.stylefeng.guns.modular.system.task;




import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.components.redis.impl.RedisDistributedLock;
import cn.stylefeng.guns.modular.system.constant.Constant;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankFlowService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static cn.stylefeng.guns.modular.system.constant.Constant.Lock.TRADE_INVALID_OVERTIME_LOCK_PREFIX;
import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.ORDER_STATUS_SUCCESS;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.BANK_CARD_LOCK_FAILED;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.LOCK_TIMEOUT;


@Component
public class TimerTask {

    private static Logger logger = LoggerFactory.getLogger(TimerTask.class);

    @Autowired
    private IBankCardService bankCardService;

    @Autowired
    private IBankFlowService iBankFlowService;

    @Autowired
    private ITradeService iTradeService;

    @Autowired
    private RedisDao redisDao;

    @Resource
    private RedisDistributedLock redisDistributedLock;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void bankOnlineStatusDetect() {
        //通过查询操作更新银行卡的数据库在线状态，缓存同步
/*        logger.info("bankOnlineStatusDetect start....");

        bankCardService.syncAllBankOnlineStatus();

        logger.info("bankOnlineStatusDetect end....");*/
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void processCashflow() {
        //通过查询操作更新银行卡的数据库在线状态，缓存同步
/*        logger.info("processCashflow start....");

        iBankFlowService.processCashflows();

        logger.info("processCashflow end....");*/
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void processAccountChange(){
        logger.info("开始批量处理帐变.....");
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void processOverDueOrders(){
        logger.info("开始批量掃描可能超时的订单.....");
        boolean locked_1 = true;
        try {
            /*添加分佈式鎖*/
            if (redisDistributedLock.lock(TRADE_INVALID_OVERTIME_LOCK_PREFIX)){
                List<Trade> list = iTradeService.findTradeInfoWithInvalidOverTime();
                if (StringUtils.isNotEmpty(list)){
                    logger.info("执行异常超时订单定时任务");
                    logger.info("总共【{}】条异常超时订单",list.size());
                    list.stream().forEach(trade -> {
                        iTradeService.invalidOrder(trade.getOrderNo());
                    });
                } else {
                    logger.info("暂无异常订单处理");
                }
            } else {
                logger.info("异常超时订单定时任务已被其他线程使用，等待释放");
                locked_1 = false;
            }
        } catch (Exception e) {
            logger.info("执行异常超时订单定时任务异常信息:{}",e);
        } finally {
            /*釋放鎖*/
            logger.info("釋放分布式锁");
            redisDistributedLock.unlock(TRADE_INVALID_OVERTIME_LOCK_PREFIX,locked_1);
        }
    }

}
