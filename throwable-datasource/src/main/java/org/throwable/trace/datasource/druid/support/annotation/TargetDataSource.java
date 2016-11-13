package org.throwable.trace.datasource.druid.support.annotation;

import java.lang.annotation.*;

/**
 * @author zhangjinci
 * @version 2016/10/12 18:10
 * @function 目标数据源注解, 方法级别注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

	String value() default "master"; //数据源名称,默认为master
}
