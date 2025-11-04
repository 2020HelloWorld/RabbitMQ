package com.itheima.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue(){
        //1.队列名
        String queueName = "simple.queue";
        //2.消息
        String msg = "Hello";
        //3.发送消息
        rabbitTemplate.convertAndSend(queueName,msg);
    }

    @Test
    public void testFanoutQueue(){
        //1.交换机名
        String exchangeName = "hmall.fanout";
        //2.消息
        String msg = "Hello,everyone!";
        //3.发送消息
        rabbitTemplate.convertAndSend(exchangeName,null,msg);
    }

    @Test
    public void testDirectQueue(){
        //1.交换机名
        String exchangeName = "hmall.direct";
        //2.消息
        String msg = "blue message!";
        //3.发送消息
        rabbitTemplate.convertAndSend(exchangeName,"blue",msg);
    }

    @Test
    public void testObjectQueue(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","Jack");
        map.put("age",21);
        //3.发送消息
        rabbitTemplate.convertAndSend("object.queue",map);
    }

    @Test
    public void testConfirmCallback() throws InterruptedException {
        //0.创建CorrelationData
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("spring amqp 处理确认结果异常",ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if (result.isAck()){
                    log.debug("收到ConfirmCallback ack，消息发送成功！");
                }else {
                    log.error("收到ConfirmCallback nack，消息发送失败！reason: {}",result.getReason());
                }
            }
        });
        //1.交换机名
        String exchangeName = "hmall.direct";
        //2.消息
        String msg = "blue message!";
        //3.发送消息
        rabbitTemplate.convertAndSend(exchangeName,"blue2",msg,cd);

        Thread.sleep(2000);
    }
}