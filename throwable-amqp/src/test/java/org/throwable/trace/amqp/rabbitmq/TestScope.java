package org.throwable.trace.amqp.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangjinci
 * @version 2016/11/23 14:30
 * @function
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestScope {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void Test1() throws Exception {
        Message message = MessageBuilder.withBody("hello rabbitmq".getBytes()).build();
        rabbitTemplate.send("exchange1", "key1", message);
    }

}
