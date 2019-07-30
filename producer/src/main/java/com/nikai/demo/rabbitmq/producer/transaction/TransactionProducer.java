package com.nikai.demo.rabbitmq.producer.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <pre>
 *     服务端确认——Transaction模式
 *
 * </pre>
 * rabbitmq com.nikai.demo.rabbitmq.producer.transaction
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 23:40 2019/7/30
 * @Modified By:
 */
public class TransactionProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queue_name = "TRANSACTION_QUEUE";
        channel.queueDeclare(queue_name, false, false, false, null);
        try {
//            将channel设置为事务模式
            channel.txSelect();
            channel.basicPublish("", queue_name, null, "transaction message".getBytes());
//            提交事务
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (IOException e) {
//            事务回滚
            channel.txRollback();
            System.out.println("消息已回滚");
        }
        channel.close();
        connection.close();

    }

}
