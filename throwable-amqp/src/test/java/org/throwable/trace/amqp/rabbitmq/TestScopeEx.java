package org.throwable.trace.amqp.rabbitmq;

import org.junit.Test;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqBindingProperties;
import org.throwable.trace.core.utils.json.JacksonMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/11/23 14:40
 * @function
 */
public class TestScopeEx {

    @Test
    public void Test1()throws Exception{
        List<RabbitmqBindingProperties> list = new ArrayList<>();
        list.add(new RabbitmqBindingProperties("queue1","exchange1","direct1","key1"));
        list.add(new RabbitmqBindingProperties("queue2","exchange2","direct2","key2"));
        list.add(new RabbitmqBindingProperties("queue3","exchange3","direct3","key3"));
        System.out.println(JacksonMapper.buildNormalMapper().toJson(list));
    }
}
