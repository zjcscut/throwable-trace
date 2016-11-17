package org.throwable.trace.core.factory;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.throwable.trace.core.utils.extend.ConcurrentHashSet;
import org.throwable.trace.core.support.spring.converter.type.StringToDateConverter;

/**
 * @author zhangjinci
 * @version 2016/11/8 16:46
 * @function 自定义转换器工厂
 */
public class CustomConverterFactory {

    private static final ConcurrentHashSet<Converter> converters = new ConcurrentHashSet<>();

    static {
        converters.add(new StringToDateConverter());
    }

    public static void addCustomConverters(FormatterRegistry registry) {
        converters.forEach(registry::addConverter);
    }
}
