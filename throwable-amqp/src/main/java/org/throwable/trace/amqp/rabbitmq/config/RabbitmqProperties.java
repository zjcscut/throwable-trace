package org.throwable.trace.amqp.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zjc
 * @version 2016/11/20 23:50
 * @description
 */
@Component
@ConfigurationProperties(prefix = "org.throwable.trace.amqp.rabbitmq")
public class RabbitmqProperties {

	private String username;
	private String password;
	private String host;
	private Integer port;
	private String virtual_host = "/";
	private Boolean publish_confirms = false;
	private Boolean publish_returns = false;
	/**
	 * 是否允许生产者并发
	 */
	private Boolean enable_producer_concurrency = false;
	private Integer producer_executor_core_pool_size;
	private Integer producer_executor_max_pool_size;
	private Integer producer_executor_queue_capacity;
	private Integer producer_executor_keep_alive_seconds;
	/**
	 * 是否允许消费者并发
	 */
	private Boolean enable_consumer_concurrency = false;
	private Integer consumer_executor_core_pool_size;
	private Integer consumer_executor_max_pool_size;
	private Integer consumer_executor_queue_capacity;
	private Integer consumer_executor_keep_alive_seconds;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getVirtual_host() {
		return virtual_host;
	}

	public void setVirtual_host(String virtual_host) {
		this.virtual_host = virtual_host;
	}

	public Boolean getPublish_confirms() {
		return publish_confirms;
	}

	public void setPublish_confirms(Boolean publish_confirms) {
		this.publish_confirms = publish_confirms;
	}

	public Boolean getPublish_returns() {
		return publish_returns;
	}

	public void setPublish_returns(Boolean publish_returns) {
		this.publish_returns = publish_returns;
	}

	public Boolean getEnable_producer_concurrency() {
		return enable_producer_concurrency;
	}

	public void setEnable_producer_concurrency(Boolean enable_producer_concurrency) {
		this.enable_producer_concurrency = enable_producer_concurrency;
	}

	public Boolean getEnable_consumer_concurrency() {
		return enable_consumer_concurrency;
	}

	public void setEnable_consumer_concurrency(Boolean enable_consumer_concurrency) {
		this.enable_consumer_concurrency = enable_consumer_concurrency;
	}

	public String buildClientApiUri() {
		return getHost() + ":" + getPort() + "/api/";
	}

	public Integer getProducer_executor_core_pool_size() {
		return producer_executor_core_pool_size;
	}

	public void setProducer_executor_core_pool_size(Integer producer_executor_core_pool_size) {
		this.producer_executor_core_pool_size = producer_executor_core_pool_size;
	}

	public Integer getProducer_executor_max_pool_size() {
		return producer_executor_max_pool_size;
	}

	public void setProducer_executor_max_pool_size(Integer producer_executor_max_pool_size) {
		this.producer_executor_max_pool_size = producer_executor_max_pool_size;
	}

	public Integer getProducer_executor_queue_capacity() {
		return producer_executor_queue_capacity;
	}

	public void setProducer_executor_queue_capacity(Integer producer_executor_queue_capacity) {
		this.producer_executor_queue_capacity = producer_executor_queue_capacity;
	}

	public Integer getProducer_executor_keep_alive_seconds() {
		return producer_executor_keep_alive_seconds;
	}

	public void setProducer_executor_keep_alive_seconds(Integer producer_executor_keep_alive_seconds) {
		this.producer_executor_keep_alive_seconds = producer_executor_keep_alive_seconds;
	}

	public Integer getConsumer_executor_core_pool_size() {
		return consumer_executor_core_pool_size;
	}

	public void setConsumer_executor_core_pool_size(Integer consumer_executor_core_pool_size) {
		this.consumer_executor_core_pool_size = consumer_executor_core_pool_size;
	}

	public Integer getConsumer_executor_max_pool_size() {
		return consumer_executor_max_pool_size;
	}

	public void setConsumer_executor_max_pool_size(Integer consumer_executor_max_pool_size) {
		this.consumer_executor_max_pool_size = consumer_executor_max_pool_size;
	}

	public Integer getConsumer_executor_queue_capacity() {
		return consumer_executor_queue_capacity;
	}

	public void setConsumer_executor_queue_capacity(Integer consumer_executor_queue_capacity) {
		this.consumer_executor_queue_capacity = consumer_executor_queue_capacity;
	}

	public Integer getConsumer_executor_keep_alive_seconds() {
		return consumer_executor_keep_alive_seconds;
	}

	public void setConsumer_executor_keep_alive_seconds(Integer consumer_executor_keep_alive_seconds) {
		this.consumer_executor_keep_alive_seconds = consumer_executor_keep_alive_seconds;
	}
}
