package org.throwable.trace.orm.hibernate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjinci
 * @version 2016/11/8 22:34
 * @function Hibernate自动配置和扫描包
 */
@Configuration
@ComponentScan(
        basePackages = {"org.throwable.trace.orm.hibernate"}
)
public class HibernateAutoConfiguration {

}
