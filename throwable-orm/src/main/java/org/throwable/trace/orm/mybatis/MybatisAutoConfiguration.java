package org.throwable.trace.orm.mybatis;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.throwable.trace.datasource.DynamicDataSource;
import org.throwable.trace.core.exception.OrmConfigException;
import org.throwable.trace.orm.mybatis.config.MybatisConfigProperties;

import java.util.Properties;

/**
 * @author zhangjinci
 * @version 2016/11/14 19:16
 * @function Mybatis自动配置
 */
@Configuration
public class MybatisAutoConfiguration {

    private final static Logger log = LoggerFactory.getLogger(MybatisAutoConfiguration.class);

    @Autowired
    private MybatisConfigProperties mybatisConfigProperties;


    //Mybatis sqlSessionFactory 在spring-mybatis包中封装为SqlSessionFactoryBean
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnBean(name = {"dataSource"})
    @ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
    public SqlSessionFactory sqlSessionFactoryBean(DynamicDataSource dynamicDataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        try {
            sqlSessionFactoryBean.setDataSource(dynamicDataSource);
            sqlSessionFactoryBean.setFailFast(true);  //快速失败
            sqlSessionFactoryBean.setTypeAliasesPackage(mybatisConfigProperties.getType_alias_package());  //实体所在包全路径名,多个用','分隔
            if (mybatisConfigProperties.mappingsLocations() != null) {
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mybatisConfigProperties.getMapping_locations()));
            }
            //分页插件配置
            PageHelper pageHelper = new PageHelper();
            Properties properties = new Properties();
            properties.setProperty("reasonable", "true"); //合理化配置
            properties.setProperty("supportMethodsArguments", "true"); //可以通过mapper传参分页
            properties.setProperty("returnPageInfo", "none"); //返回Page对象
            pageHelper.setProperties(properties);
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});

            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error("Create  mybatis sqlSessionFactory,failed,message:{}", e.getMessage());
            throw new OrmConfigException(e);
        }
    }

    //Mybatis sqlSessionTemplate
    @Bean(name = "sqlSessionTemplate")
    @ConditionalOnBean(name = "sqlSessionFactory")
    @ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "active", havingValue = "true")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = mybatisConfigProperties.getExecutorType();
        if (executorType == null) {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
        return new SqlSessionTemplate(sqlSessionFactory, executorType);
    }

    //Mybatis transactionManager
    @Bean(name = "transactionManager")
    @ConditionalOnBean(name = "sqlSessionFactory")
    @ConditionalOnProperty(prefix = "org.throwable.trace.orm.mybatis", name = "enable_transaction", havingValue = "true")
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }


}
