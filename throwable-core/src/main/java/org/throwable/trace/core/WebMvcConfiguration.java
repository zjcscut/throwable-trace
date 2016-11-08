package org.throwable.trace.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.throwable.trace.core.factory.CustomArgumentResolverFactory;
import org.throwable.trace.core.factory.CustomConverterFactory;

import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/11/8 16:37
 * @function Mvc相关配置
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


    //添加自定义类型转换器
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        CustomConverterFactory.addCustomConverters(registry);
    }

    //添加自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    //添加自定义参数转换器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        CustomArgumentResolverFactory.addCustomArgumentResolvers(argumentResolvers);
    }
}
