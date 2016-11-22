package org.throwable.trace.amqp.rabbitmq.support;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.throwable.trace.amqp.rabbitmq.config.ExchangeTypeEnum;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqBindingProperties;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqProperties;
import org.throwable.trace.core.utils.json.JacksonMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjc
 * @version 2016/11/21 23:47
 * @description rabbitmq绑定配置解析器
 */
@Component
@AutoConfigureAfter(value = RabbitmqProperties.class)
public class BindingConfigResolver {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    private List<RabbitmqBindingProperties> bindingList;


    public BindingConfigResolver() throws Exception {
        init();
    }

    private void init() throws Exception {
        bindingList = JacksonMapper.parseListFromJsonFile(rabbitmqProperties.getConfig_file_location(), RabbitmqBindingProperties.class);
    }

    public List<Binding> buildBindings() {
        List<Binding> bindings = new ArrayList<>(bindingList.size());
        for (RabbitmqBindingProperties bindingSingle : bindingList) {
            Binding binding = BindingBuilder.bind(createQueue(bindingSingle.getQueueName()))
                    .to(createExchange(bindingSingle.getExchangeName(),bindingSingle.getExchangeType()))
                    .with(bindingSingle.getRoutingKey()).noargs();
            bindings.add(binding);
        }
        return bindings;
    }


    private Binding transfer

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
