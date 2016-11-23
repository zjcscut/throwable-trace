package org.throwable.trace.amqp.rabbitmq.config;

/**
 * @author zhangjinci
 * @version 2016/11/22 21:13
 * @function 交换器类型枚举
 */
public enum ExchangeTypeEnum {

    DEFAULT("direct"),

    DIRECT("direct"),

    FANOUT("fanout"),

    HEADERS("headers"),

    TOPIC("topic");

    ExchangeTypeEnum(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

}
