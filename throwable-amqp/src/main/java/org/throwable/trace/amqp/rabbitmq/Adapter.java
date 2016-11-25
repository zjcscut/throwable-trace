package org.throwable.trace.amqp.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.throwable.trace.amqp.rabbitmq.support.ConfirmCallbackResolver;

/**
 * @author zhangjinci
 * @version 2016/11/24 14:28
 * @function
 */
public class Adapter {

    public void Confirm(ConfirmCallbackResolver resolver, RabbitTemplate rabbitTemplate, CorrelationData correlationData, boolean ack, String cause){
          rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
              @Override
              public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                  resolver.comfirmHandler(correlationData, ack, cause);
              }
          });
    }
}
