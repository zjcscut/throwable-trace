package org.throwable.trace.datasource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjinci
 * @version 2016/11/10 20:51
 * @function 数据源自动配置
 */
@Configuration
@ComponentScan(
        basePackages = "org.throwable.trace.datasource.druid"
)
public class DataSourceConfiguration {
}
