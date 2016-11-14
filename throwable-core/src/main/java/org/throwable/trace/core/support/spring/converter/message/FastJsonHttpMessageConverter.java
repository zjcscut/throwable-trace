package org.throwable.trace.core.support.spring.converter.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author zhangjinci
 * @version 2016/11/8 17:08
 * @function fastJson httpMessage转换器
 */
@Configuration
@ConditionalOnClass({JSON.class})
public class FastJsonHttpMessageConverter {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");


    @Configuration
    @ConditionalOnClass({FastJsonHttpMessageConverter4.class})
    @ConditionalOnProperty(
            name = {"spring.http.converters.preferred-json-mapper"},
            havingValue = "fastjson",
            matchIfMissing = true
    )
    protected static class FastJson2HttpMessageConverterConfiguration {

        protected FastJson2HttpMessageConverterConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean({FastJsonHttpMessageConverter4.class})
        public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4() {
            FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
            FastJsonConfig config = new FastJsonConfig();
            config.setSerializerFeatures(
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue
            );
            config.setDateFormat("yyyy-MM-dd HH:mm:ss");  //转换的日期格式
            ValueFilter valueFilter = (object, name, value) -> {
                if (null == value) {
                    value = "";
                }
                return value;
            };
            config.setSerializeFilters(valueFilter);
            converter.setFastJsonConfig(config);
            converter.setDefaultCharset(DEFAULT_CHARSET);
            return converter;
        }
    }
}
