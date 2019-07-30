package com.nikai.demo.rabbitmq.producer.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq com.nikai.demo.rabbitmq.producer.confirm
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 0:18 2019/7/31
 * @Modified By:
 */
public class AsyncConfirmProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String ASYNC_CONFIRM_QUEUE = "ASYNC_CONFIRM_QUEUE";
        channel.queueDeclare(ASYNC_CONFIRM_QUEUE, false, false, false, null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", ASYNC_CONFIRM_QUEUE, null, ("aysnc message_" + i).getBytes());
        }

//        异步监听确认和未确认的消息
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("borkery已经确认消息，标识：" + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(String.format("borker未确认消息，标识：%d,多个消息：%b", deliveryTag, multiple));
            }
        });
        System.out.println("执行完毕");
    }

}
