package org.throwable.trace.core.support.spring.request.annotation;

import java.lang.annotation.*;
import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/10/31 12:10
 * @function 用于接收特殊表单参数的参数注解
 * match()的Class数组必须加入相应的类型转换器，详情见{@link org.throwable.trace.core.support.spring.converter.type.StringToDateConverter}
 * 添加类型转换器见{@link cn.zjc.config.WebMvcConfiguration}
 */
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomParam {

    String value() default "";

    Class<?>[] match() default {Date.class};
}
