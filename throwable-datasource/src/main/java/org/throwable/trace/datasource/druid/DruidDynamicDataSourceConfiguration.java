package org.throwable.trace.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.throwable.trace.core.datasource.DynamicDataSource;
import org.throwable.trace.datasource.druid.props.DruidBasicProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/11/11 11:31
 * @function 德鲁伊动态数据源配置
 */
@Configuration
public class DruidDynamicDataSourceConfiguration {

    @Autowired
    private DruidBasicProperties druidBasicProperties;

    //Druid动态数据源配置
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        return dynamicDataSource;
    }


    public DruidDataSource buildDruidDataSource(String url, String driverClassName, String userName, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidBasicProperties.buildBasicDruidConfiguration(druidDataSource);
        return druidDataSource;
    }


}
