package org.throwable.trace.amqp.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zjc
 * @version 2016/11/20 23:50
 * @description rabbitmq属性配置
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
	 * 是否允许连接并发
	 */
	private Boolean enable_connection_concurrency = false;
	private Integer connection_executor_core_pool_size;
	private Integer connection_executor_max_pool_size;
	private Integer connection_executor_queue_capacity;
	private Integer connection_executor_keep_alive_seconds;
	/**
	 * 是否允许消费者并发
	 */
	private Boolean enable_consumer_concurrency = false;
	private Integer consumer_executor_core_pool_size;
	private Integer consumer_executor_max_pool_size;
	private Integer consumer_executor_queue_capacity;
	private Integer consumer_executor_keep_alive_seconds;
	private Integer concurrent_consumers;
	private Integer max_concurrent_consumers;

	private String acknowledge_mode;

	/**
	 * mq配置文件:json文件
	 */
	private String config_file_location;


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


	public Boolean getEnable_consumer_concurrency() {
		return enable_consumer_concurrency;
	}

	public void setEnable_consumer_concurrency(Boolean enable_consumer_concurrency) {
		this.enable_consumer_concurrency = enable_consumer_concurrency;
	}

	public String buildClientApiUri() {
		return getHost() + ":" + getPort() + "/api/";
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

	public Boolean getEnable_connection_concurrency() {
		return enable_connection_concurrency;
	}

	public void setEnable_connection_concurrency(Boolean enable_connection_concurrency) {
		this.enable_connection_concurrency = enable_connection_concurrency;
	}

	public Integer getConnection_executor_core_pool_size() {
		return connection_executor_core_pool_size;
	}

	public void setConnection_executor_core_pool_size(Integer connection_executor_core_pool_size) {
		this.connection_executor_core_pool_size = connection_executor_core_pool_size;
	}

	public Integer getConnection_executor_max_pool_size() {
		return connection_executor_max_pool_size;
	}

	public void setConnection_executor_max_pool_size(Integer connection_executor_max_pool_size) {
		this.connection_executor_max_pool_size = connection_executor_max_pool_size;
	}

	public Integer getConnection_executor_queue_capacity() {
		return connection_executor_queue_capacity;
	}

	public void setConnection_executor_queue_capacity(Integer connection_executor_queue_capacity) {
		this.connection_executor_queue_capacity = connection_executor_queue_capacity;
	}

	public Integer getConnection_executor_keep_alive_seconds() {
		return connection_executor_keep_alive_seconds;
	}

	public void setConnection_executor_keep_alive_seconds(Integer connection_executor_keep_alive_seconds) {
		this.connection_executor_keep_alive_seconds = connection_executor_keep_alive_seconds;
	}

	public Integer getConcurrent_consumers() {
		return concurrent_consumers;
	}

	public void setConcurrent_consumers(Integer concurrent_consumers) {
		this.concurrent_consumers = concurrent_consumers;
	}

	public Integer getMax_concurrent_consumers() {
		return max_concurrent_consumers;
	}

	public void setMax_concurrent_consumers(Integer max_concurrent_consumers) {
		this.max_concurrent_consumers = max_concurrent_consumers;
	}

	public String getAcknowledge_mode() {
		return acknowledge_mode;
	}

	public void setAcknowledge_mode(String acknowledge_mode) {
		this.acknowledge_mode = acknowledge_mode;
	}

	public String getConfig_file_location() {
		return config_file_location;
	}

	public void setConfig_file_location(String config_file_location) {
		this.config_file_location = config_file_location;
	}

	public AcknowledgeMode exchangeAcknowledgeMode() {
		AcknowledgeMode acknowledgeMode;
		switch (getAcknowledge_mode().toLowerCase()) {
			case "none":
				acknowledgeMode = AcknowledgeMode.NONE;
				break;
			case "auto":
				acknowledgeMode = AcknowledgeMode.AUTO;
				break;
			case "manual":
				acknowledgeMode = AcknowledgeMode.MANUAL;
				break;
			default: {
				acknowledgeMode = AcknowledgeMode.NONE;
			}
		}
		return acknowledgeMode;
	}
}
