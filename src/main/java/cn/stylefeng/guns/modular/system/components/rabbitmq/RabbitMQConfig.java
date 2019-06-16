package cn.stylefeng.guns.modular.system.components.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.stylefeng.guns.modular.system.constant.Constant.MessageRoute.MSG_ROUTE_ORDER;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-12 9:04
 **/
@Configuration
public class RabbitMQConfig {

    public static final String EX_PROCESSTASK = "ex_processor";

    //处理队列
    public static final String QUEUE_ORDER_PROCESS = "queue_order_process";

    //路由

    //消费者并发数量
    public static final int DEFAULT_CONCURRENT = 10;

    @Bean("customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        factory.setMaxConcurrentConsumers(DEFAULT_CONCURRENT);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * 交换机配置
     * @return the exchange
     */
    @Bean(EX_PROCESSTASK)
    public Exchange EX_MEDIA_VIDEOTASK() {
        return ExchangeBuilder.directExchange(EX_PROCESSTASK).durable(true).build();
    }
    //声明队列
    @Bean("QUEUE_ORDER_PROCESS")
    public Queue QUEUE_PROCESSTASK() {
        Queue queue = new Queue(QUEUE_ORDER_PROCESS,true,false,true);
        return queue;
    }
    /**
     * 绑定队列到交换机 .
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding binding_queue_order_processtask(@Qualifier("QUEUE_ORDER_PROCESS") Queue queue, @Qualifier(EX_PROCESSTASK) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MSG_ROUTE_ORDER).noargs();
    }
}

