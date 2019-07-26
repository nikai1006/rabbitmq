package com.nikai.demo.rabbitmq.producer.priority;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.HashMap;

/**
 * 优先级队列
 * <pre>
 *     优先级高的消息可以优先被消费，但是：只有消息堆积（消息的发送速度大于消费者的消费速度）的情况下优先级
 * 才有意义。
 * </pre>
 */
public class PriorityQueue {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //设置一个队列的最大优先级
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10); //队列最大优先级
        String priority_queue = "PRIORITY_QUEUE";
        channel.queueDeclare(priority_queue, false, false, false, arguments);
//        发送消息时指定消息当前的优先级
        BasicProperties properties = new Builder()
            .priority(5)
            .build();
        channel.basicPublish("", priority_queue, properties, "I'm priority message".getBytes());
        channel.close();
        connection.close();
    }

}
