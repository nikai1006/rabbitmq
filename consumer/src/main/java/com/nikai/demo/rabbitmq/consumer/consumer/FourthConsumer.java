package com.nikai.demo.rabbitmq.consumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * rabbitmq com.nikai.demo.rabbitmq.consumer.consumer
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 0:45 2019/7/24
 * @Modified By:
 */
@Component
@RabbitListener(queues = "FOURTH_QUEUE")
public class FourthConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println(msg);
    }

}
