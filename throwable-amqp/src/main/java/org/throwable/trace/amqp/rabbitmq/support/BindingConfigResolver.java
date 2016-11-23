package org.throwable.trace.amqp.rabbitmq.support;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqBindingProperties;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqProperties;
import org.throwable.trace.core.utils.json.JacksonMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjc
 * @version 2016/11/21 23:47
 * @description rabbitmq绑定配置解析器
 */
@Component
@AutoConfigureAfter(value = RabbitmqProperties.class)
public class BindingConfigResolver implements InitializingBean{

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    private List<RabbitmqBindingProperties> bindingList;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() throws Exception {
        bindingList = JacksonMapper.parseListFromJsonFile(rabbitmqProperties.getConfig_file_location(), RabbitmqBindingProperties.class);
    }

    public List<Binding> buildBindings() {
        List<Binding> bindings = new ArrayList<>(bindingList.size());
        for (RabbitmqBindingProperties bindingSingle : bindingList) {
            bindings.add(transferBinding(bindingSingle));
        }
        return bindings;
    }

    /**
     * 根据Exchange类型进行Binding装换
     *
     * @param bindingSingle bindingSingle
     * @return Binding
     */
    private Binding transferBinding(RabbitmqBindingProperties bindingSingle) {
        Binding binding;
        switch (bindingSingle.getExchangeType().toLowerCase()) {
            case "direct":
                binding = BindingBuilder.bind(createQueue(bindingSingle.getQueueName()))
                        .to(new DirectExchange(bindingSingle.getExchangeName()))
                        .with(bindingSingle.getRoutingKey());
                break;
            case "topic":
                binding = BindingBuilder.bind(createQueue(bindingSingle.getQueueName()))
                        .to(new TopicExchange(bindingSingle.getExchangeName()))
                        .with(bindingSingle.getRoutingKey());
                break;
            case "headers":
                /**
                 * 只提供 whereAll() key value同时匹配
                 */
                binding = BindingBuilder.bind(createQueue(bindingSingle.getQueueName()))
                        .to(new HeadersExchange(bindingSingle.getExchangeName()))
                        .whereAll(exchangeHeaderToMap(bindingSingle.getRoutingKey()))
                        .match();
                break;
            case "fanout":
                binding = BindingBuilder.bind(createQueue(bindingSingle.getQueueName()))
                        .to(new FanoutExchange(bindingSingle.getExchangeName()));
                break;
            default: {
                binding = BindingBuilder.bind(createQueue(bindingSingle.getQueueName()))
                        .to(createExchange(bindingSingle.getExchangeName(), bindingSingle.getExchangeType()))
                        .with(bindingSingle.getRoutingKey()).noargs();
            }
        }
        return binding;
    }

    private Map<String, Object> exchangeHeaderToMap(String key) {
        String[] mapEntrys = key.split(";");
        Map<String, Object> result = new HashMap<>(mapEntrys.length);
        for (String mapEntry : mapEntrys) {
            String[] entry = mapEntry.split("=");
            result.put(entry[0], entry[1]);
        }
        return result;
    }

    public List<Queue> buildQueues() {
        List<Queue> queues = new ArrayList<>(bindingList.size());
        for (RabbitmqBindingProperties queue : bindingList) {
            queues.add(createQueue(queue.getQueueName()));
        }
        return queues;
    }

    /**
     * 构造队列对象,默认持久化
     *
     * @param name name
     * @return Queue
     */
    private Queue createQueue(String name) {
        return new Queue(name, true);
    }


    public List<Exchange> buildExchanges() {
        List<Exchange> exchanges = new ArrayList<>(bindingList.size());
        for (RabbitmqBindingProperties exchange : bindingList) {
            exchanges.add(createExchange(exchange.getExchangeName(), exchange.getExchangeType()));
        }
        return exchanges;
    }

    /**
     * 构造交换器对象,默认持久化
     *
     * @param name name
     * @return Exchange
     */
    private Exchange createExchange(String name, String type) {
        return transferExchanges(name, type);
    }


    private Exchange transferExchanges(String name, String type) {
        Exchange exchange;
        switch (type.toLowerCase()) {
            case "direct":
                exchange = new DirectExchange(name);
                break;
            case "topic":
                exchange = new TopicExchange(name);
                break;
            case "headers":
                exchange = new HeadersExchange(name);
                break;
            case "fanout":
                exchange = new FanoutExchange(name);
                break;
            default: {
                exchange = new CustomExchange(name, type, true, false);
            }
        }
        return exchange;
    }


}
