package org.throwable.trace.amqp.rabbitmq.support;


import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author zhangjinci
 * @version 2016/11/24 11:54
 * @function
 */
public abstract class ConfirmCallbackResolver {

    public void comfirm(CorrelationData correlationData, boolean ack, String cause) {
        comfirmHandler(correlationData, ack, cause);
    }

    public abstract void comfirmHandler(CorrelationData correlationData, boolean ack, String cause);

}
