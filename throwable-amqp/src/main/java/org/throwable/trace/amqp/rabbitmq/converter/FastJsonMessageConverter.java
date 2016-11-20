package org.throwable.trace.amqp.rabbitmq.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.*;
import org.throwable.trace.core.utils.json.FastJsonUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author zhangjinci
 * @version 2016/11/4 11:12
 * @function FastJson消息转换器
 */
public class FastJsonMessageConverter extends AbstractJsonMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(FastJsonMessageConverter.class);
    private ObjectMapper jsonObjectMapper = new ObjectMapper();
    private ClassMapper classMapper;
    private Jackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();


    public FastJsonMessageConverter() {
        super();
        classMapper = new DefaultClassMapper();
    }

    public Jackson2JavaTypeMapper getJavaTypeMapper() {
        return javaTypeMapper;
    }

    @Override
    protected Message createMessage(Object o, MessageProperties messageProperties) {
        byte[] data;
        try {
            String source = FastJsonUtils.toJson(o);
            data = source.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("FastJsonMessageConverter createMessage failed,message:" + e.getMessage());
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(DEFAULT_CHARSET);
        messageProperties.setContentLength(data.length);
        if (this.getClassMapper() == null) {
            this.getJavaTypeMapper().fromJavaType(this.jsonObjectMapper.constructType(o.getClass()), messageProperties);
        } else {
            classMapper.fromClass(o.getClass(), messageProperties);
        }
        return new Message(data, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            if (StringUtils.isNotBlank(contentType) && StringUtils.contains(contentType, "json")) {
                String encoding = properties.getContentEncoding();
                if (StringUtils.isBlank(encoding)) {
                    encoding = DEFAULT_CHARSET;
                }
                try {
                    if (this.getClassMapper() == null) {
                        JavaType e = this.getJavaTypeMapper().toJavaType(message.getMessageProperties());
                        content = this.convertBytesToObject(message.getBody(), encoding, e);
                    } else {
                        Class<?> targetClass = classMapper.toClass(properties);
                        content = convertBytesToObject(message.getBody(), encoding, targetClass);
                    }
                } catch (Exception e) {
                    log.error("FastJsonMessageConverter fromMessage failed,message:" + e.getMessage());
                    throw new MessageConversionException(
                            "Failed to convert Message content", e);
                }
            } else {
                log.warn("Could not convert incoming message with content-type ["
                        + contentType + "]");
            }
        }
        if (content == null) {  //如果转换失败,直接转换
            content = message.getBody();
        }
        return content;
    }

    private Object convertBytesToObject(final byte[] data, final String encoding, Class<?> target) throws UnsupportedEncodingException {
        String convertStr = new String(data, encoding);
        return FastJsonUtils.toBean(convertStr, target);
    }

    private Object convertBytesToObject(final byte[] data, final String encoding, JavaType target) throws UnsupportedEncodingException {
        String convertStr = new String(data, encoding);
        return FastJsonUtils.toBean(convertStr, target);
    }
}
