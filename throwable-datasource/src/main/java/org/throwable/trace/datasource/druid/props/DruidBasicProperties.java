package org.throwable.trace.datasource.druid.props;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.throwable.trace.core.exception.DataSourceException;

import java.sql.SQLException;

/**
 * @author zhangjinci
 * @version 2016/11/10 20:50
 * @function Druid数据源基础配置
 */
@Component
@ConditionalOnProperty(prefix = "org.throwable.trace.druid")
public class DruidBasicProperties {

	//基础配置
	private Integer initialSize = 5;
	private Integer maxActive = 10;
	private Integer minIdle = 3;
	private Integer maxWait = 60000;
	private Integer minEvictableIdleTimeMillis = 60000;
	private String validationQuery = "SELECT 1 FROM";
	private Boolean testWhileIdle = true;
	private Boolean testOnBorrow = false;
	private Boolean testOnReturn = false;
	private Boolean poolPreparedStatements = true;
	private Integer maxPoolPreparedStatementPerConnectionSize = 50;
	private String filters = "stat";
	private Boolean removeAbandoned = true;
	private Integer removeAbandonedTimeout = 1800;

	//org.throwable.trace.datasource.druid.stat_enable = true时候才配置
	//druid WebStatFilter配置
	private String stat_enable = "true";
	private String stat_url = "/druid/*";
	//ip白名单,以逗号分隔
	private String stat_allow = "localhost";
	//ip黑名单,以逗号分隔
	private String stat_deny;

	private String stat_username = "admin";
	private String stat_password = "123456";

	private String stat_reset_enable = "false";

	//org.throwable.trace.datasource.druid.web_filter_enable = true时候才配置
	//#druid WebStatFilter配置
	private String web_filter_enable = "true";
	private String webFilter_url = "/*";
	private String webfilter_exclusions = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*";


	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public Boolean getRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(Boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public Integer getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public Boolean getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}


	public String getStat_url() {
		return stat_url;
	}

	public void setStat_url(String stat_url) {
		this.stat_url = stat_url;
	}

	public String getStat_allow() {
		return stat_allow;
	}

	public void setStat_allow(String stat_allow) {
		this.stat_allow = stat_allow;
	}

	public String getStat_deny() {
		return stat_deny;
	}

	public void setStat_deny(String stat_deny) {
		this.stat_deny = stat_deny;
	}

	public String getStat_username() {
		return stat_username;
	}

	public void setStat_username(String stat_username) {
		this.stat_username = stat_username;
	}

	public String getStat_password() {
		return stat_password;
	}

	public void setStat_password(String stat_password) {
		this.stat_password = stat_password;
	}

	public String getStat_reset_enable() {
		return stat_reset_enable;
	}

	public void setStat_reset_enable(String stat_reset_enable) {
		this.stat_reset_enable = stat_reset_enable;
	}

	public String getWebFilter_url() {
		return webFilter_url;
	}

	public void setWebFilter_url(String webFilter_url) {
		this.webFilter_url = webFilter_url;
	}

	public String getWebfilter_exclusions() {
		return webfilter_exclusions;
	}

	public void setWebfilter_exclusions(String webfilter_exclusions) {
		this.webfilter_exclusions = webfilter_exclusions;
	}

	public String getStat_enable() {
		return stat_enable;
	}

	public void setStat_enable(String stat_enable) {
		this.stat_enable = stat_enable;
	}

	public String getWeb_filter_enable() {
		return web_filter_enable;
	}

	public void setWeb_filter_enable(String web_filter_enable) {
		this.web_filter_enable = web_filter_enable;
	}

	//注入Druid基础配置项
	public void buildBasicDruidConfiguration(DruidDataSource druidDataSource) {
		druidDataSource.setInitialSize(getInitialSize());
		druidDataSource.setMaxActive(getMaxActive());
		druidDataSource.setMinIdle(getMinIdle());
		druidDataSource.setMaxWait(getMaxWait());
		druidDataSource.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
		druidDataSource.setValidationQuery(getValidationQuery());
		druidDataSource.setTestWhileIdle(getTestWhileIdle());
		druidDataSource.setTestOnBorrow(getTestOnBorrow());
		druidDataSource.setPoolPreparedStatements(getPoolPreparedStatements());
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(getMaxPoolPreparedStatementPerConnectionSize());
		try {
			druidDataSource.setFilters(getFilters());
		} catch (SQLException e) {
			throw new DataSourceException("set druid filters fail:" + e.getMessage());
		}
		druidDataSource.setRemoveAbandoned(getRemoveAbandoned());
		druidDataSource.setRemoveAbandonedTimeout(getRemoveAbandonedTimeout());
	}


	//注册一个StatViewServlet
	//ip:host/druid/
	@Bean
	@ConditionalOnProperty(prefix = "org.throwable.trace.druid.stat_enable", havingValue = "true")
	public ServletRegistrationBean DruidServlet() {
		ServletRegistrationBean servletRegistrationBean
				= new ServletRegistrationBean(new StatViewServlet(), getStat_url()); //添加一个Servlet
		//添加初始化参数：initParams
		//添加白名单
		servletRegistrationBean.addInitParameter("allow", getStat_allow());
		//添加黑名单(优先于白名单)
		servletRegistrationBean.addInitParameter("deny", getStat_deny());
		//登录查看信息的账号密码
		servletRegistrationBean.addInitParameter("loginUsername", getStat_username());  //Druid后台登录账号
		servletRegistrationBean.addInitParameter("loginPassword", getStat_password()); //Druid后台登录密码
		//是否能够重置数据
		servletRegistrationBean.addInitParameter("resetEnable", getStat_reset_enable());
		return servletRegistrationBean;
	}

	//注册一个WebStatFilter
	//匹配所有路径/*
	@Bean
	@ConditionalOnProperty(prefix = "org.throwable.trace.druid.web_filter_enable", havingValue = "true")
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean
				= new FilterRegistrationBean(new WebStatFilter());
		//Url匹配规则
		filterRegistrationBean.addUrlPatterns(getWebFilter_url());
		//忽略的资源文件
		filterRegistrationBean.addInitParameter("exclusions", getWebfilter_exclusions());
		return filterRegistrationBean;
	}
}
