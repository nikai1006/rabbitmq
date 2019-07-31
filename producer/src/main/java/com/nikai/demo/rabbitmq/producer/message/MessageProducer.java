package com.nikai.demo.rabbitmq.producer.message;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 幂等性需要通过给消息添加唯一id来实现
 * rabbitmq com.nikai.demo.rabbitmq.producer.message
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 23:56 2019/7/31
 * @Modified By:
 */
public class MessageProducer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> headers = new HashMap<>(2);
        headers.put("name", "nikai");

        BasicProperties properties = new Builder()
            .deliveryMode(2) //2代表持久化
            .contentEncoding("UTF-8")
            .expiration("10000") //TTL
            .headers(headers) //自定义属性
            .priority(5) //优先级，默认为5，配合队列的x-max-priority属性使用
            .messageId(String.valueOf(UUID.randomUUID())) //消息唯一id
            .build();

        String UUID_MSG_QUEUE = "UUID_MSG_QUEUE";
        channel.queueDeclare(UUID_MSG_QUEUE, false, false, false, null);
        channel.basicPublish("", UUID_MSG_QUEUE, properties, "UUID message".getBytes());
        channel.close();
        connection.close();

    }

}
