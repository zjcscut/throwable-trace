package org.throwable.trace.orm.mybatis;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.throwable.trace.core.datasource.DynamicDataSource;
import org.throwable.trace.core.exception.OrmConfigException;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author zhangjinci
 * @version 2016/11/14 19:16
 * @function Mybatis自动配置
 */
@Configuration
@ComponentScan(
		basePackages = {"org.throwable.trace.orm.mybatis"}
)
public class MybatisAutoConfiguration {


	//Mybatis sqlSessionFactory 在spring-mybatis包中封装为SqlSessionFactoryBean
	@Bean(name = "sqlSessionFactory")
	@ConditionalOnBean(name = {"dataSource"})
	@ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
	public SqlSessionFactory sqlSessionFactoryBean(DynamicDataSource dynamicDataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dynamicDataSource);
		sqlSessionFactoryBean.setFailFast(true);  //快速失败
		sqlSessionFactoryBean.setTypeAliasesPackage();  //实体所在包全路径名,多个用','分隔
		sqlSessionFactoryBean.setMapperLocations(); //mapping.xml文件的类路径
		//分页插件配置
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true"); //合理化配置
		properties.setProperty("supportMethodsArguments", "true"); //可以通过mapper传参分页
		properties.setProperty("returnPageInfo", "none"); //返回Page对象
//		properties.setProperty("dialect", "mysql");  //数据库方言,4.0版本后会自动判断
		pageHelper.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
		try {
			return sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			throw new OrmConfigException(e);
		}
	}

	//Mybatis sqlSessionTemplate
	@Bean(name = "sqlSessionTemplate")
	@ConditionalOnBean(name = "sqlSessionFactory")
	@ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	//Mybatis transactionManager
	@Bean(name = "transactionManager")
	@ConditionalOnBean(name = "sqlSessionFactory")
	@ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
	public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
		return new DataSourceTransactionManager(dynamicDataSource);
	}

	//tk-> Mybatis mapperScanner  用于配置通用mapper
	public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		return mapperScannerConfigurer;
	}


}
