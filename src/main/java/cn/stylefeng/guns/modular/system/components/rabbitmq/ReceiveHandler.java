package cn.stylefeng.guns.modular.system.components.rabbitmq;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.constant.Constant;
import cn.stylefeng.guns.modular.system.dto.*;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankRemarkService;
import cn.stylefeng.guns.modular.system.service.IFlowDataService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_ALIPAY;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_BANK;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_WXCHAT;
import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.ORDER_STATUS_PROCESS;
import static cn.stylefeng.guns.modular.system.constant.Constant.orderRoute.CREATE_ORDER;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.ACCOUNT_SELECT_FAILED;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.BANK_CARD_SELECT_SUC;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.MSG_COMMUNICATE_ERROR;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-17 21:21
 **/
//@Component
public class ReceiveHandler {


    @Autowired
    private ITradeService tradeService;

    private static Logger logger = LoggerFactory.getLogger(ReceiveHandler.class);

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_ORDER_PROCESS})
    public void orderHandler(String msg,Message message,
                             Channel channel){
        try {
            logger.info("receive raw message is {}",msg);
            Map map = JSON.parseObject(msg, Map.class);
            Integer op =(Integer)map.get("op");
            String orderNo = (String) map.get("orderNo");

            if(StringUtils.isEmpty(op)
                    ||StringUtils.isEmpty(orderNo))
                throw new ServiceException(MSG_COMMUNICATE_ERROR);

            if(op==CREATE_ORDER){
                tradeService.createOrder(orderNo);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = {RabbitMQConfig.QUEUE_FLOW_PROCESS})
    public void matchFlowHandler(String msg,Message message,
                             Channel channel){
        try {
            logger.info("receive raw message is {}",msg);
            Map map = JSON.parseObject(msg, Map.class);
            Integer flowNo =(Integer)map.get("flowNo");

            if(StringUtils.isEmpty(flowNo)
                    ||StringUtils.isEmpty(flowNo))
                throw new ServiceException(MSG_COMMUNICATE_ERROR);

            //上分匹配逻辑
            tradeService.matchOrder(flowNo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}