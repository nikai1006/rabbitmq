package com.nikai.demo.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class RabbitmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqConsumerApplication.class, args);
	}

}
