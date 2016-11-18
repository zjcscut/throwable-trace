package org.throwable.trace.amqp.rabbitmq.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.throwable.trace.core.utils.extend.Assert;

/**
 * @author zhangjinci
 * @version 2016/11/4 15:35
 * @function 创建每个发送消息的绑定id
 */
public class MessageBuildHelper {

    private static final String SEPERATOR = ":";
    private static final String DEFAULT_EXCHANGER = "default-exchanger";
    private static final String DEFAULT_ROUTINGKEY = "default-routingKey";
    private static final String DEFAULT_ID = String.valueOf(System.currentTimeMillis());

    public static String buildCorrelationData(final String exchanger, final String routingKey, final String id) {
        String _id = id;
        String _exchanger = exchanger;
        String _routingKey = routingKey;
        if (StringUtils.isBlank(id)) {
            _id = DEFAULT_ID;
        }
        if (StringUtils.isBlank(exchanger)) {
            _exchanger = DEFAULT_EXCHANGER;
        }
        if (StringUtils.isBlank(routingKey)) {
            _routingKey = DEFAULT_ROUTINGKEY;
        }
        return _exchanger + SEPERATOR + _routingKey + SEPERATOR + _id;
    }

    public static CorrelationData createCorrelationData(final String exchanger, final String routingKey, final String id) {
        return new CorrelationData(buildCorrelationData(exchanger, routingKey, id));
    }

    public static CorrelationData createCorrelationData(final String exchanger, final String routingKey) {
        return new CorrelationData(buildCorrelationData(exchanger, routingKey, null));
    }

    public Message buildMessageBody(String body, String encoding, String contentType) {
        Assert.notBlank(body, "message body must not be blank!");
        return MessageBuilder.withBody(body.getBytes()).setContentEncodingIfAbsent(encoding).setContentType(contentType).build();
    }
}
