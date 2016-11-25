package org.throwable.trace.amqp.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.throwable.trace.amqp.rabbitmq.config.RabbitmqProperties;
import org.throwable.trace.amqp.rabbitmq.converter.FastJsonMessageConverter;
import org.throwable.trace.amqp.rabbitmq.support.BindingConfigResolver;
import org.throwable.trace.amqp.rabbitmq.support.ConfirmCallbackResolver;
import org.throwable.trace.core.exception.AmqpException;
import org.throwable.trace.core.utils.SystemContext;

import java.util.List;

/**
 * @author zjc
 * @version 2016/11/21 0:06
 * @description rabbitmq 配置
 */
@Configuration
public class RabbitmqConfiguration {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Autowired
    private BindingConfigResolver bindingConfigResolver;

    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfiguration.class);

    /**
     * 消息连接工厂线程池
     *
     * @see org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     */
    @Bean(name = "connectionThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor connectionThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(rabbitmqProperties.getConnection_executor_core_pool_size());  //核心池数量,这个过小会阻塞消费者队列,最好比MaxConcurrentConsumers大
        executor.setMaxPoolSize(rabbitmqProperties.getConnection_executor_max_pool_size()); //最大池数量
        executor.setQueueCapacity(rabbitmqProperties.getConnection_executor_queue_capacity()); //队列长度
        executor.setKeepAliveSeconds(rabbitmqProperties.getConnection_executor_keep_alive_seconds()); //当队列满并且最大池数量也满,多余的线程等待的最大时间
        return executor;
    }

    /**
     * 消息消费者线程池
     *
     * @see org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     */
    @Bean(name = "consumerThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor consumerThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(rabbitmqProperties.getConsumer_executor_core_pool_size());  //核心池数量,这个过小会阻塞消费者队列,最好比MaxConcurrentConsumers大
        executor.setMaxPoolSize(rabbitmqProperties.getConsumer_executor_max_pool_size()); //最大池数量
        executor.setQueueCapacity(rabbitmqProperties.getConsumer_executor_queue_capacity()); //队列长度
        executor.setKeepAliveSeconds(rabbitmqProperties.getConsumer_executor_keep_alive_seconds()); //当队列满并且最大池数量也满,多余的线程等待的最大时间
        return executor;
    }

    /**
     * 带缓存的连接工厂
     *
     * @param connectionThreadPoolTaskExecutor executor
     * @return connectionFactory
     */
    @Bean(name = "connectionFactory")
    public CachingConnectionFactory connectionFactory(@Autowired @Qualifier("connectionThreadPoolTaskExecutor")
                                                              ThreadPoolTaskExecutor connectionThreadPoolTaskExecutor) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(rabbitmqProperties.getUsername());
        connectionFactory.setPassword(rabbitmqProperties.getPassword());
        connectionFactory.setHost(rabbitmqProperties.getHost());
        connectionFactory.setPort(rabbitmqProperties.getPort());
        connectionFactory.setVirtualHost(rabbitmqProperties.getVirtual_host());
        connectionFactory.setPublisherConfirms(rabbitmqProperties.getPublish_confirms());  //必须手工设置确认==>发布确认
        connectionFactory.setPublisherReturns(rabbitmqProperties.getPublish_returns());  //允许错误路由的发布消息返回
        connectionFactory.setExecutor(connectionThreadPoolTaskExecutor);
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
     * @param consumerThreadPoolTaskExecutor        consumerThreaPoolTaskExecutor
     * @param contentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter
     * @return simpleRabbitListenerContainerFactory
     * @see SimpleRabbitListenerContainerFactory
     */
    @Bean(name = "simpleRabbitListenerContainerFactory")
    @ConditionalOnBean(name = {"contentTypeDelegatingMessageConverter"})
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(CachingConnectionFactory connectionFactory,
                                                                                     @Autowired @Qualifier("consumerThreadPoolTaskExecutor")
                                                                                             ThreadPoolTaskExecutor consumerThreadPoolTaskExecutor,
                                                                                     ContentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setTaskExecutor(consumerThreadPoolTaskExecutor);
        factory.setConcurrentConsumers(rabbitmqProperties.getConcurrent_consumers());  //异步消费者数量
        factory.setMaxConcurrentConsumers(rabbitmqProperties.getMax_concurrent_consumers()); //最大异步消费者数量
        factory.setMessageConverter(contentTypeDelegatingMessageConverter); //设置字符串转换器
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(rabbitmqProperties.exchangeAcknowledgeMode()); //设置确认模式
        return factory;
    }


    /**
     * AmqpAdmin:职能类似进行Rabbit的Admin操作,可以添加交换器、队列和绑定等等
     *
     * @see RabbitAdmin
     * 设置交换机，针对消费者配置
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加Headers属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean(name = "amqpAdmin")
    @ConditionalOnBean(name = "connectionFactory")
    public AmqpAdmin amqpAdmin(CachingConnectionFactory connectionFactory) {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        try {
            List<Queue> queues = bindingConfigResolver.buildQueues();
            for (Queue queue : queues) {
                log.debug("Rabbitmq AmqpAdmin declareQueue :" + queue.getName());
                amqpAdmin.declareQueue(queue);
            }
            List<Exchange> exchanges = bindingConfigResolver.buildExchanges();
            for (Exchange exchange : exchanges) {
                log.debug("Rabbitmq AmqpAdmin declareExchange :" + exchange.getName());
                amqpAdmin.declareExchange(exchange);
            }
            List<Binding> bindings = bindingConfigResolver.buildBindings();
            for (Binding binding : bindings) {
                log.debug("Rabbitmq AmqpAdmin declareBinding :" + binding.getDestination());
                amqpAdmin.declareBinding(binding);
            }
        } catch (Exception e) {
            throw new AmqpException(e);
        }
        return amqpAdmin;
    }

    /**
     * RabbitTemplate rabbitmq操作模板
     *
     * @param connectionFactory                     connectionFactory
     * @param contentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter
     * @return RabbitTemplate
     * <p>
     * 发布消息返回?什么时候会出现这种情况？
     * 对于返回消息，模板的mandatory属性必须被设定为true
     * 确认消息是否到达broker服务器，也就是只确认是否正确到达exchange中即可，只要正确的到达exchange中，broker即可确认该消息返回给客户端ack。
     * 换句话说,只要有ReturnCallback说明消息没有发送到broker服务器,或者说没有正确到达exchange,就是错误路由
     * 错误路由的话一般不应该出现,属于比较严重的逻辑错误,采用重新推送到x-exception-message队列(候选方案)
     * 或者可以选择把返回的消息入库或者放进去Nosql
     * <p>
     * 发布消息确认,这个其实是broker的ack
     * 实际上消息丢失了(路由错误)，却仍旧可以收到来自服务器的Ack。这也是实际使用中容易犯的错误
     * 也就是broker的comfirm机制是无法确认消息正确路由,返回的Ack只是确认消息发送到了broker
     */
    @Bean(name = "rabbitTemplate")
    @ConditionalOnBean(name = {"contentTypeDelegatingMessageConverter", "connectionFactory"})
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory,
                                         ContentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setEncoding(SystemContext.UTF8_CHARSET);
        rabbitTemplate.setMessageConverter(contentTypeDelegatingMessageConverter);
        rabbitTemplate.setMandatory(rabbitmqProperties.getMandatory());
        if (rabbitmqProperties.getPublish_confirms()) {
            rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    if (ack) {
                        log.debug("Broker comfirm succeed,CorrelationData id==>" + correlationData.getId());
                    } else {
                        log.debug("Broker comfirm failed,CorrelationData id==>" + correlationData.getId() + ";cause:" + cause);
                    }
                }
            });
        }
        //
        if (rabbitmqProperties.getPublish_returns() && rabbitmqProperties.getMandatory()) {
            final RabbitTemplate errorTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                @Override
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    String errorMsg = "route failed and try to republish again,replyCode:" + replyCode + ",replyText:" + replyText;
                    log.error(errorMsg);
                    RepublishMessageRecoverer recoverer = new RepublishMessageRecoverer(errorTemplate);
                    Throwable cause = new Exception(errorMsg);
                    recoverer.recover(message, cause);
                }
            });
        }
        return rabbitTemplate;
    }

}
