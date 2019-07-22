package com.nikai.demo.rabbitmq.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * rabbitmq com.nikai.demo.rabbitmq.producer.config
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 0:58 2019/7/22
 * @Modified By:
 */
@Configuration
@PropertySource("classpath:rbmq.properties")
public class RabbitMQConfig {

    @Value("FIRST_QUEUE")
    private String firstQueue;
    @Value("SECOND_QUEUE")
    private String secondQueue;
    @Value("THIRD_QUEUE")
    private String thirdQueue;
    @Value("FOURTH_QUEUE")
    private String fourthQueue;

    @Value("DIRECT_EXCHANGE")
    private String directExchange;
    @Value("TOPIC_EXCHANGE")
    private String topicExchange;

    @Value("FANOUT_EXCHANGE")
    private String fanoutExchange;

    //    创建四个队列
    @Bean("firstQueue")
    public Queue getFirstQueue() {
        return new Queue(firstQueue);
    }

    @Bean("secondQueue")
    public Queue getSecondQueue() {
        return new Queue(secondQueue);
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue() {
        return new Queue(thirdQueue);
    }

    @Bean("fourthQueue")
    public Queue getFourthQueue() {
        return new Queue(fourthQueue);
    }

    //    创建三个交换机
    @Bean("directExchange")
    public DirectExchange getDirectExchange() {
        return new DirectExchange(directExchange);
    }

    @Bean("topicExchange")
    public TopicExchange getTopicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean("fanoutExchange")
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange(fanoutExchange);

    }
//创建四个绑定关系

    @Bean
    public Binding bindFirst(@Qualifier("firstQueue") Queue queue,
        @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("nikai.queue");
    }

    @Bean
    public Binding bindSeond(@Qualifier("secondQueue") Queue queue,
        @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.nikai.*");

    }

    @Bean
    public Binding bindThird(@Qualifier("thirdQueue") Queue queue,
        @Qualifier("fanoutExchange") FanoutExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFourth(@Qualifier("fourthQueue") Queue queue,
        @Qualifier("fanoutExchange") FanoutExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange);
    }
}
