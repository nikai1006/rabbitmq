package com.nikai.demo.rabbitmq.producer.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.HashMap;

/**
 * <pre>
 *     有三种情况消息会进入DLX（Dead Letter Exchange）死信交换机。
 * 1、(NACK || Reject ) && requeue == false
 * 2、消息过期
 * 3、队列达到最大长度（先入队的消息会被发送到DLX）
 * </pre>
 */
public class DeadLetterQueue {

    /**
     * <pre>
     *     可以设置一个死信队列（Dead Letter Queue）与DLX绑定，即可以存储Dead Letter，消费者可以监听这个队列取
     * 走消息。
     * </pre>
     */
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //指定这个队列的死信交换机
        HashMap<String, Object> parameters = new HashMap<>();
        String dlx_exchange = "DLX_EXCHANGE";
        parameters.put("x-dead-letter-exchange", dlx_exchange);
        channel.queueDeclare("DLQ_QUEUE", false, false, false, parameters);
//        声明死信交换机
        channel.exchangeDeclare(dlx_exchange, "topic", false, false, false, null);
//        声明死信队列
        String dlu_queue = "DLU_QUEUE";
        channel.queueDeclare(dlu_queue, false, false, false, null);
        channel.queueBind(dlu_queue, dlx_exchange, "#");

        channel.basicPublish("", "", null, "dlx msg".getBytes());
        channel.close();
        connection.close();

    }
}
