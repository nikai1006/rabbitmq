package com.nikai.demo.rabbitmq.producer.ttl;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

public class TTLQueue {

    public static void main(String[] args) throws Exception {
        String nikai_ttl_queue = "NIKAI_TTL_QUEUE";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
//        通过队列属性设置消息过期时间
        Map<String, Object> argsz = new HashMap<>();
        argsz.put("x-message-ttl", 60000);
        channel.queueDeclare(nikai_ttl_queue, false, false, false, argsz);

//        设置单条消息的过期时间
        BasicProperties properties = new Builder()
            .deliveryMode(2) //持久化消息
            .contentEncoding("UTF-8")
            .expiration("10000")
            .build();
        channel.basicPublish("", nikai_ttl_queue, properties, "I'm a ttl message".getBytes());
        channel.close();
        connection.close();

    }
}
