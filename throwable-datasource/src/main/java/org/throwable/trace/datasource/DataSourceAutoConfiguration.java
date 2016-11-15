package org.throwable.trace.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.throwable.trace.core.datasource.DataSourceContext;
import org.throwable.trace.core.datasource.DynamicDataSource;
import org.throwable.trace.datasource.druid.props.DataSourceProperties;

import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/11/10 20:51
 * @function 数据源自动配置
 */
@Configuration
@ComponentScan(
        basePackages = "org.throwable.trace.datasource.druid"
)
public class DataSourceAutoConfiguration {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    //Druid动态数据源配置
    //这里Bean的名字必须指定为"dataSource",因为spring-jdbc需要这个bean
    @Bean(name = "dataSource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = dataSourceProperties.buildTargetDynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        //如果定义多个master为true的数据源,前边的全部会被最后一个覆盖
        for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
            if (entry.getKey() != null && ((DataSourceContext) entry.getKey()).getMaster()) {
                dynamicDataSource.setDefaultTargetDataSource(entry.getValue());
            }
        }
        return dynamicDataSource;
    }
}
