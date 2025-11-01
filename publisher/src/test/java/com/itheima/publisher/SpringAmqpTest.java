package com.itheima.publisher;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
}