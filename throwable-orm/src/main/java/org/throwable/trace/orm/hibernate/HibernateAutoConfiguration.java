package org.throwable.trace.orm.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.throwable.trace.core.datasource.DynamicDataSource;
import org.throwable.trace.orm.hibernate.config.HibernateConfigProperties;

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

	@Autowired
	private HibernateConfigProperties hibernateConfigProperties;

	//Hibernate sessionFactory
	@Bean(name = "sessionFactory")
	@ConditionalOnBean(name = {"dynamicDataSource"})
	@ConditionalOnProperty(value = "org.throwable.trace.orm.hibernate.active", havingValue = "true")
	public LocalSessionFactoryBean sessionFactory(DynamicDataSource dynamicDataSource) {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dynamicDataSource); //指定动态数据源
		sessionFactoryBean.setPackagesToScan(hibernateConfigProperties.packagesToScan()); //实体所在的包,不定参数，可以多传
		sessionFactoryBean.setHibernateProperties(hibernateConfigProperties.buildHibernateProperties()); //指定hbm.xml的classpath路径
		sessionFactoryBean.setMappingDirectoryLocations(hibernateConfigProperties.mappingsDirectoryLocations());
		return sessionFactoryBean;
	}


	//Hibernate事务管理器
	@Bean
	@ConditionalOnBean(name = {"dynamicDataSource","sessionFactory"})
	@ConditionalOnProperty(prefix = "org.throwable.trace.orm.hibernate.enable_transaction", havingValue = "true")
	public HibernateTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory(dynamicDataSource).getObject());
		return transactionManager;
	}


}
