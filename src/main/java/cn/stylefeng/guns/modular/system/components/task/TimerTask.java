package cn.stylefeng.guns.modular.system.task;




import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TimerTask {

    private static Logger logger = LoggerFactory.getLogger(TimerTask.class);

    @Autowired
    private IBankCardService bankCardService;

    @Autowired
    private IBankFlowService iBankFlowService;


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

}
