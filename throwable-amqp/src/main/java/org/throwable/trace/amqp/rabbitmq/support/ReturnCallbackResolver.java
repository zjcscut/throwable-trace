package org.throwable.trace.amqp.rabbitmq.support;

        import org.springframework.amqp.core.Message;

/**
 * @author zhangjinci
 * @version 2016/11/24 11:54
 * @function
 */
public interface ReturnCallbackResolver {

    void handleReturnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey);
}
