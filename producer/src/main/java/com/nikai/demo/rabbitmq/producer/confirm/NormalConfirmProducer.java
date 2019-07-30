package com.nikai.demo.rabbitmq.producer.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <pre>
 *     服务端确认——Confirm模式
 * </pre>
 * rabbitmq com.nikai.demo.rabbitmq.producer.confirm
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 23:59 2019/7/30
 * @Modified By:
 */
public class NormalConfirmProducer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String CONFIRM_QUEUE = "CONFIRM_QUEUE";
        channel.queueDeclare(CONFIRM_QUEUE, false, false, false, null);
//        将channel设置为confirm模式
        channel.confirmSelect();
        channel.basicPublish("", CONFIRM_QUEUE, null, "confirm message".getBytes());
//        p普通confirm，发送一条，确认一条
        if (channel.waitForConfirms()) {
            System.out.println("消息发送成功");
        }
        channel.close();
        connection.close();
    }
}
