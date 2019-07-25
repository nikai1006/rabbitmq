package com.nikai.demo.rabbitmq.producer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbitmq com.nikai.demo.rabbitmq.producer.producer
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 1:12 2019/7/24
 * @Modified By:
 */
@Component
public class Producer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send() {
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "nikai.queue", "a direct msg");
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE", "w.nikai.n", "a topic msg");
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", "a fanout msg");

    }
}
