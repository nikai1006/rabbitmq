package com.nikai.demo.rabbitmq.producer.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq com.nikai.demo.rabbitmq.producer.confirm
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 0:09 2019/7/31
 * @Modified By:
 */
public class BatchConfirmProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String BATCH_CONFIRM_QUEUE = "BATCH_CONFIRM_QUEUE";
        try {
            channel.queueDeclare(BATCH_CONFIRM_QUEUE, false, false, false, null);
            channel.confirmSelect();
            for (int i = 0; i < 10; i++) {
//                发送消息
                channel.basicPublish("", BATCH_CONFIRM_QUEUE, null, "batch confirm message".getBytes());
            }
//            批量确认结果
//            直到所有消息都发送，只要有一个未被broker确认就会出现异常
            channel.waitForConfirmsOrDie();
            System.out.println("消息发送完毕");
        } catch (InterruptedException e) {
//            发生异常，可能需要对所有消息进行重发
            e.printStackTrace();

        }
        channel.close();
        connection.close();
    }

}
