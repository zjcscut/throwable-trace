package org.throwable.trace.amqp.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqProperties;
import org.throwable.trace.amqp.rabbitmq.converter.FastJsonMessageConverter;
import org.throwable.trace.core.concurrency.executor.MonitingThreaPoolTaskExecutor;

/**
 * @author zjc
 * @version 2016/11/21 0:06
 * @description rabbitmq 配置
 */
@Configuration
public class RabbitmqConfiguration {

	@Autowired
	private RabbitmqProperties rabbitmqProperties;

	/**
	 * 消息连接工厂线程池
	 *
	 * @see MonitingThreaPoolTaskExecutor
	 * @see org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
	 */
	@Bean(name = "connectionThreaPoolTaskExecutor")
	@ConditionalOnProperty(prefix = "org.throwable.trace.amqp.rabbitmq", name = "enable_connection_concurrency", havingValue = "true")
	public MonitingThreaPoolTaskExecutor connectionThreaPoolTaskExecutor() {
		MonitingThreaPoolTaskExecutor executor = new MonitingThreaPoolTaskExecutor();
		executor.setCorePoolSize(rabbitmqProperties.getConnection_executor_core_pool_size());  //核心池数量,这个过小会阻塞消费者队列,最好比MaxConcurrentConsumers大
		executor.setMaxPoolSize(rabbitmqProperties.getConnection_executor_max_pool_size()); //最大池数量
		executor.setQueueCapacity(rabbitmqProperties.getConnection_executor_queue_capacity()); //队列长度
		executor.setKeepAliveSeconds(rabbitmqProperties.getConnection_executor_keep_alive_seconds()); //当队列满并且最大池数量也满,多余的线程等待的最大时间
		return executor;
	}

	/**
	 * 消息消费者线程池
	 *
	 * @see MonitingThreaPoolTaskExecutor
	 * @see org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
	 */
	@Bean(name = "consumerThreaPoolTaskExecutor")
	@ConditionalOnProperty(prefix = "org.throwable.trace.amqp.rabbitmq", name = "enable_consumer_concurrency", havingValue = "true")
	public MonitingThreaPoolTaskExecutor consumerThreaPoolTaskExecutor() {
		MonitingThreaPoolTaskExecutor executor = new MonitingThreaPoolTaskExecutor();
		executor.setCorePoolSize(rabbitmqProperties.getConsumer_executor_core_pool_size());  //核心池数量,这个过小会阻塞消费者队列,最好比MaxConcurrentConsumers大
		executor.setMaxPoolSize(rabbitmqProperties.getConsumer_executor_max_pool_size()); //最大池数量
		executor.setQueueCapacity(rabbitmqProperties.getConsumer_executor_queue_capacity()); //队列长度
		executor.setKeepAliveSeconds(rabbitmqProperties.getConsumer_executor_keep_alive_seconds()); //当队列满并且最大池数量也满,多余的线程等待的最大时间
		return executor;
	}

	/**
	 * 带缓存的连接工厂
	 *
	 * @param connectionThreaPoolTaskExecutor executor
	 * @return connectionFactory
	 */
	@Bean(name = "connectionFactory")
	public CachingConnectionFactory connectionFactory(@Autowired(required = false) @Qualifier("connectionThreaPoolTaskExecutor")
															  MonitingThreaPoolTaskExecutor connectionThreaPoolTaskExecutor) {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(rabbitmqProperties.getUsername());
		connectionFactory.setPassword(rabbitmqProperties.getPassword());
		connectionFactory.setHost(rabbitmqProperties.getHost());
		connectionFactory.setPort(rabbitmqProperties.getPort());
		connectionFactory.setVirtualHost(rabbitmqProperties.getVirtual_host());
		connectionFactory.setPublisherConfirms(rabbitmqProperties.getPublish_confirms());  //必须手工设置确认==>发布确认
		connectionFactory.setPublisherReturns(rabbitmqProperties.getPublish_returns());  //允许错误路由的发布消息返回
		if (connectionThreaPoolTaskExecutor != null) {
			connectionFactory.setExecutor(connectionThreaPoolTaskExecutor);
		}
		return connectionFactory;
	}

	/**
	 * 提供Rabbitmq客户端操作
	 *
	 * @link http://host:port/api/
	 * @see AmqpManagementOperations
	 */
	@Bean(name = "amqpManagementOperations")
	public AmqpManagementOperations amqpManagementOperations() {
		String uri = rabbitmqProperties.buildClientApiUri();
		return new RabbitManagementTemplate(
				uri, rabbitmqProperties.getUsername(), rabbitmqProperties.getPassword());
	}

	/**
	 * 这个类是在1.4.2版本中引入的，并可基于MessageProperties的contentType属性允许委派给一个特定的MessageConverter.
	 * 默认情况下，如果没有contentType属性或值没有匹配配置转换器时，它会委派给SimpleMessageConverter.
	 *
	 * @return converter
	 * @see FastJsonMessageConverter
	 * @see org.springframework.amqp.support.converter.SimpleMessageConverter
	 */
	@Bean(name = "contentTypeDelegatingMessageConverter")
	public ContentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter() {
		ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();
		converter.addDelegate(MediaType.APPLICATION_JSON_VALUE, new FastJsonMessageConverter()); //添加处理application/json消息的转换器
		converter.addDelegate(MediaType.TEXT_PLAIN_VALUE, new Jackson2JsonMessageConverter());
		return converter;
	}

	/**
	 * 消费者容器
	 *
	 * @param connectionFactory                     connectionFactory
	 * @param consumerThreaPoolTaskExecutor         consumerThreaPoolTaskExecutor
	 * @param contentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter
	 * @return simpleRabbitListenerContainerFactory
	 * @see SimpleRabbitListenerContainerFactory
	 */
	@Bean(name = "simpleRabbitListenerContainerFactory")
	@ConditionalOnBean(name = {"contentTypeDelegatingMessageConverter"})
	public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(CachingConnectionFactory connectionFactory,
																					 @Autowired(required = false) @Qualifier("consumerThreaPoolTaskExecutor")
																							 MonitingThreaPoolTaskExecutor consumerThreaPoolTaskExecutor,
																					 ContentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		if (consumerThreaPoolTaskExecutor != null) {
			factory.setTaskExecutor(consumerThreaPoolTaskExecutor);
			factory.setConcurrentConsumers(rabbitmqProperties.getConcurrent_consumers());  //异步消费者数量
			factory.setMaxConcurrentConsumers(rabbitmqProperties.getMax_concurrent_consumers()); //最大异步消费者数量
		}
		factory.setMessageConverter(contentTypeDelegatingMessageConverter); //设置字符串转换器
		factory.setConnectionFactory(connectionFactory);
		factory.setAcknowledgeMode(rabbitmqProperties.exchangeAcknowledgeMode()); //设置确认模式
		return factory;
	}


	/**
	 *  AmqpAdmin:职能类似进行Rabbit的Admin操作,可以添加交换器、队列和绑定等等
	 *  @see RabbitAdmin
	 * 设置交换机，针对消费者配置
	 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	 * HeadersExchange ：通过添加Headers属性key-value匹配
	 * DirectExchange:按照routingkey分发到指定队列
	 * TopicExchange:多关键字匹配
	 */
	@Bean(name = "amqpAdmin")
	public AmqpAdmin amqpAdmin(CachingConnectionFactory connectionFactory) {
		AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);

		AbstractExchange directExchange = new DirectExchange("directExchange", true, false);
		amqpAdmin.declareExchange(directExchange);  //添加Exchange
		Queue queue = new Queue("myQueue", true);
		amqpAdmin.declareQueue(queue);      //添加队列
		Binding binding = BindingBuilder.bind(queue).to(directExchange).with("route-key").noargs();
		amqpAdmin.declareBinding(binding);   //添加Binding
		return amqpAdmin;
	}


}
