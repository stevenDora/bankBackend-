package cn.stylefeng.guns.modular.system.components.rabbitmq;

import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.stylefeng.guns.modular.system.constant.Constant.MessageRoute.MSG_ROUTE_ORDER;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.DEPOSIT_FAILED_CREATE_ORDER_MSG_SUBMIT;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.UN_KNOW_ERROR;

@Service
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    public void sendMsg(String msg,String route){
        logger.info("sendMsg  -> msg:{},routeKey:{}",msg,route);

        if(StringUtils.isEmpty(msg)||StringUtils.isEmpty(route)){
            throw new ServiceException(UN_KNOW_ERROR);
        }
        //向MQ发送视频处理消息
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_PROCESSTASK,route,msg);
        } catch (AmqpException e) {
            e.printStackTrace();
            if(route == MSG_ROUTE_ORDER){
                throw new ServiceException(DEPOSIT_FAILED_CREATE_ORDER_MSG_SUBMIT);
            }
            else {
                throw new ServiceException(UN_KNOW_ERROR);
            }
        }
    }
}
