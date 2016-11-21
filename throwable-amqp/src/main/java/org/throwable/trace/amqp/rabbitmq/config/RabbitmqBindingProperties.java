package org.throwable.trace.amqp.rabbitmq.config;

/**
 * @author zjc
 * @version 2016/11/21 23:44
 * @description rabbitmq绑定配置
 */
public class RabbitmqBindingProperties {

	private String queueName;
	private String exchangeName;
	private String exchangeType;
	private String routingKey;

	public RabbitmqBindingProperties() {
	}

	public RabbitmqBindingProperties(String queueName, String exchangeName,
									 String exchangeType, String routingKey) {
		this.queueName = queueName;
		this.exchangeName = exchangeName;
		this.exchangeType = exchangeType;
		this.routingKey = routingKey;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
}
