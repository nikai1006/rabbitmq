package com.nikai.demo.rabbitmq.producer.returnlistener;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq com.nikai.demo.rabbitmq.producer.returnlistener
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 0:33 2019/7/31
 * @Modified By:
 */
public class ReturnListener {

    public static void main(String[] args) throws IOException, TimeoutException, Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost:5672");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.addReturnListener(((replyCode, replyText, exchange, routingKey, properties, body) -> {
            System.out.println("监听器收到了无法路由的被返回的消息");
            System.out.println("replyText:" + replyText);
            System.out.println("exchange:" + exchange);
            System.out.println("routingKey:" + routingKey);
            System.out.println("body:" + new String(body));
        }));

        BasicProperties properties = new Builder().deliveryMode(2).contentEncoding("UTF-8").build();
        channel.basicPublish("", "nikai.rabbit", true, properties, "带返回的消息".getBytes());

        TimeUnit.SECONDS.sleep(10);
        channel.close();
        connection.close();

    }

}
