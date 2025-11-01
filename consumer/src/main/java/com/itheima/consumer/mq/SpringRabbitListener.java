package com.itheima.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenerQueueMessage(String s){
        log.info("spring消费者接收到消息：【" + s + "】");
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenerFanoutQueue1(String s){
        log.info("spring消费者1接收到消息：【" + s + "】");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenerFanoutQueue2(String s){
        log.info("spring消费者2接收到消息：【" + s + "】");
    }

    @RabbitListener(queues = "direct.queue1")
    public void listenerDirectQueue1(String s){
        log.info("spring消费者1接收到消息：【" + s + "】");
    }

    @RabbitListener(queues = "direct.queue2")
    public void listenerDirectQueue2(String s){
        log.info("spring消费者2接收到消息：【" + s + "】");
    }

    /**
     * 基于注解声明队列、交换机等
     * @param s
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "test.queue",durable = "true"),
            exchange = @Exchange(name = "test.direct",durable = "true",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}
    ))
    public void listenerTestMessage(String s){
        log.info("spring消费者接收到消息：【" + s + "】");
    }

    @RabbitListener(queues = "object.queue")
    public void listenerObjectMessage(Map<String,Object> s){
        log.info("listenerObjectMessage消费者接收到消息：【" + s + "】");
    }
}
