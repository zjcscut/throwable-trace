package org.throwable.trace.core.utils;

/**
 * @author zjc
 * @version 2016/11/6 10:57
 * @description 系统常量
 */
public interface SystemContext {

    Integer DEFAULT_PAGESIZE = 20;

    String UTF8_CHARSET = "UTF-8";

    Integer RABBITMQ_CONNECTION_EXECUTOR_CORE_POOL_SIZE = 10;
    Integer RABBITMQ_CONNECTION_EXECUTOR_MAX_POOL_SIZE = 50;
    Integer RABBITMQ_CONNECTION_EXECUTOR_QUEUE_CAPACITY = 128;
    Integer RABBITMQ_CONNECTION_EXECUTOR_KEEP_ALIVE_SECONDS = 60;

    Integer RABBITMQ_CONSUMER_EXECUTOR_CORE_POOL_SIZE = 15;
    Integer RABBITMQ_CONSUMER_EXECUTOR_MAX_POOL_SIZE = 50;
    Integer RABBITMQ_CONSUMER_EXECUTOR_QUEUE_CAPACITY = 128;
    Integer RABBITMQ_CONSUMER_EXECUTOR_KEEP_ALIVE_SECONDS = 60;
    Integer RABBITMQ_CONCURRENT_CONSUMERS = 5;
    Integer RABBITMQ_MAX_CONCURRENT_CONSUMERS = 10;

    String RABBITMQ_VIRTUAL_HOST = "/";
}
