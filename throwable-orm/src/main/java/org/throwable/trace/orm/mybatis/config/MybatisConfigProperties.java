package org.throwable.trace.orm.mybatis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zjc
 * @version 2016/11/14 22:32
 * @description Mybatis属性配置
 */
@Component
@ConfigurationProperties(prefix = "org.throwable.trace.orm.mybatis")
public class MybatisConfigProperties {

	
}
