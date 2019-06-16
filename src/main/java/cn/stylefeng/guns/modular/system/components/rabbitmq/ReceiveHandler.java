package cn.stylefeng.guns.modular.system.components.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-17 21:21
 **/
@Component
public class ReceiveHandler {

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_ORDER_PROCESS})
    public void send_email(String msg,Message message,Channel channel){
        System.out.println("receive message is:"+msg);
    }

}