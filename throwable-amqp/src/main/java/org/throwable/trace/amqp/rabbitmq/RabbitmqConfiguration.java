package org.throwable.trace.amqp.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqProperties;

/**
 * @author zjc
 * @version 2016/11/21 0:06
 * @description rabbitmq 配置
 */
@Configuration
public class RabbitmqConfiguration {

	@Autowired
	private RabbitmqProperties rabbitmqProperties;
}
