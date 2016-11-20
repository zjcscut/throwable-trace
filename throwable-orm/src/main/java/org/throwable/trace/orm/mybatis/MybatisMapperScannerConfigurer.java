package org.throwable.trace.orm.mybatis;


import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.throwable.trace.core.utils.extend.ResourceUtils;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author zhangjinci
 * @version 2016/11/15 10:11
 * @function MapperScannerConfigurer必须在配置加载之后加载, 否则会拿不到配置值, 抛出空指针异常
 * 针对加载快这个问题,使用折衷的解决方案。初始化直接从配置文件取值。
 */
@Configuration
public class MybatisMapperScannerConfigurer implements InitializingBean {

	private final static Logger log = LoggerFactory.getLogger(MybatisMapperScannerConfigurer.class);

	private String basePackages;
	private String mappers;

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("load MybatisMapperScannerConfigurer config from yml,yaml or properties file");
		Resource[] ymlResources = ResourceUtils.getResourcesByPathPattern("classpath:*.yml");
		Resource[] yamlResources = ResourceUtils.getResourcesByPathPattern("classpath:*.yaml");
		Resource[] propResources = ResourceUtils.getResourcesByPathPattern("classpath:*.properties");
		Assert.isTrue(!(ymlResources.length == 0 && yamlResources.length == 0 && propResources.length == 0), "could not find any config file");
		if (yamlResources.length > 0 || ymlResources.length > 0) {
			YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
			for (Resource ymlResource : ymlResources) {
				basePackages = getValueFromYmlPropertySource(yamlPropertySourceLoader, "org.throwable.trace.orm.mybatis", ymlResource, "org.throwable.trace.orm.mybatis.base_package");
				mappers = getValueFromYmlPropertySource(yamlPropertySourceLoader, "org.throwable.trace.orm.mybatis", ymlResource, "org.throwable.trace.orm.mybatis.mappers");
			}
			for (Resource yamlResource : yamlResources) {
				basePackages = getValueFromYmlPropertySource(yamlPropertySourceLoader, "org.throwable.trace.orm.mybatis", yamlResource, "org.throwable.trace.orm.mybatis.base_package");
				mappers = getValueFromYmlPropertySource(yamlPropertySourceLoader, "org.throwable.trace.orm.mybatis", yamlResource, "org.throwable.trace.orm.mybatis.mappers");
			}
		}
		if (propResources.length > 0) {
			PropertiesPropertySourceLoader propertySourceLoader = new PropertiesPropertySourceLoader();
			for (Resource propResource : propResources) {
				basePackages = getValueFromPropPropertySource(propertySourceLoader, "org.throwable.trace.orm.mybatis", propResource, "org.throwable.trace.orm.mybatis.base_package");
				mappers = getValueFromPropPropertySource(propertySourceLoader, "org.throwable.trace.orm.mybatis", propResource, "org.throwable.trace.orm.mybatis.mappers");
			}
		}
	}

	private String getValueFromPropPropertySource(PropertiesPropertySourceLoader loader, String prifix, Resource resource, String key) throws Exception {
		PropertySource<?> propertySource = loader.load(prifix, resource, null);
		return propertySource.getProperty(key) != null ? propertySource.getProperty(key).toString() : null;
	}

	private String getValueFromYmlPropertySource(YamlPropertySourceLoader loader,
												 String prifix, Resource resource, String key) throws Exception {
		PropertySource<?> propertySource = loader.load(prifix, resource, null);
		return propertySource.getProperty(key) != null ? propertySource.getProperty(key).toString() : null;
	}

	//tk-> MybatisMapperScannerConfigurer mapperScanner  用于配置通用mapper
	//这个类实现了 InitializingBean,加载顺序靠前
	@Bean
	@ConditionalOnBean(name = {"sqlSessionFactory"})
	@ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		Validate.notBlank(getBasePackages(), "could not find base package config");
		mapperScannerConfigurer.setBasePackage(getBasePackages());
		Validate.notBlank(getMappers(), "could not find mappers config");
		Properties properties = new Properties();
		properties.setProperty("mappers", getMappers());
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	public String getMappers() {
		return mappers;
	}

	public void setMappers(String mappers) {
		this.mappers = mappers;
	}
}
