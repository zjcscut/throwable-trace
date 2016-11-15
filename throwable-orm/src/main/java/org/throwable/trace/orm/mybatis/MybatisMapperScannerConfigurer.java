package org.throwable.trace.orm.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author zhangjinci
 * @version 2016/11/15 10:11
 * @function MapperScannerConfigurer必须在配置加载之后加载, 否则会拿不到配置值, 抛出空指针异常
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MybatisMapperScannerConfigurer {

    @Value("${org.throwable.trace.orm.mybatis.base_package}")
    private String basePackages;
    @Value("${org.throwable.trace.orm.mybatis.mappers}")
    private String mappers;

    //tk-> MybatisMapperScannerConfigurer mapperScanner  用于配置通用mapper
    @Bean
    @ConditionalOnBean(name = {"sqlSessionFactory"})
    @ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage(mybatisConfigProperties.getBase_package());  //mapper 接口所在的包全路径名,多个包用','或者';'分隔
        mapperScannerConfigurer.setBasePackage("org.throwable.trace.orm.mybatis.mapper");
        Properties properties = new Properties();
//        properties.setProperty("mappers", mybatisConfigProperties.getMappers()); //通用mapper全类名,多个用','分隔,业务mapper继承于其中的通用mapper
        properties.setProperty("mappers","org.throwable.trace.orm.mybatis.api.common.CustomCommonMapper");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
