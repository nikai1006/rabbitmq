package com.nikai.demo.rabbitmq.producer.producer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * rabbitmq com.nikai.demo.rabbitmq.producer.producer
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 0:01 2019/7/26
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private Producer producer;

    @Test
    public void send() {
        producer.send();
    }
}