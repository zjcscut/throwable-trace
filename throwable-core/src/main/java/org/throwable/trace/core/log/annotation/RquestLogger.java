package org.throwable.trace.core.log.annotation;

import java.lang.annotation.*;

/**
 * @author zhangjinci
 * @version 2016/11/9 11:48
 * @function 请求日志注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RquestLogger {

    //是否输出到控制台
    boolean print() default false;

    //是否允许持久化
    boolean enablePersistence() default false;

    String value() default "request-logger";

    String description() default "request-logger-descprition";
}
