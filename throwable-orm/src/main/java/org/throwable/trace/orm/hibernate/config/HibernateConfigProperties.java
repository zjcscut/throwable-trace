package org.throwable.trace.orm.hibernate.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author zjc
 * @version 2016/11/7 0:39
 * @description Hibernate属性配置, 给定默认值就行, 如果找不到就默认注入
 */
@Component
@ConfigurationProperties(prefix = "org.throwable.trace.orm.hibernate")
public class HibernateConfigProperties {


	private String show_sql = "true";
	private String format_sql = "false";
	private String hbm2ddl_auto = "update";
	private String dialect = "org.hibernate.dialect.MySQL5Dialect";
	private String use_second_level_cache = "false";
	private String use_query_cache = "false";
	private String use_sql_comments = "false";
	private String default_batch_fetch_size = "8";
	private String query_cache_factory = "org.hibernate.cache.internal.StandardQueryCache";
	private String auto_commit = "false";
	private String isolation = "4";
	private String jdbc_batch_size = "20";


	public Properties buildHibernateProperties() {
		Properties props = new Properties();
		props.put("hibernate.show_sql", getShow_sql());
		props.put("hibernate.format_sql", getFormat_sql());
		props.put("hibernate.dialect", getDialect());
		props.put("hibernate.hbm2ddl.auto", getHbm2ddl_auto());
		props.put("hibernate.cache.use_second_level_cache", getUse_second_level_cache());
		props.put("hibernate.cache.query_cache_factory", getUse_query_cache());
		props.put("hibernate.use_sql_comments", getUse_sql_comments());
		props.put("hibernate.default_batch_fetch_size", getDefault_batch_fetch_size());
		props.put("hibernate.cache.query_cache_factory", getQuery_cache_factory());
		props.put("hibernate.connection.autocommit", getAuto_commit());
		props.put("hibernate.connection.isolation", getIsolation());
		props.put("hibernate.jdbc.batch_size", getJdbc_batch_size());
		return props;
	}


	public String getShow_sql() {
		return show_sql;
	}

	public void setShow_sql(String show_sql) {
		this.show_sql = show_sql;
	}

	public String getFormat_sql() {
		return format_sql;
	}

	public void setFormat_sql(String format_sql) {
		this.format_sql = format_sql;
	}

	public String getHbm2ddl_auto() {
		return hbm2ddl_auto;
	}

	public void setHbm2ddl_auto(String hbm2ddl_auto) {
		this.hbm2ddl_auto = hbm2ddl_auto;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getUse_second_level_cache() {
		return use_second_level_cache;
	}

	public void setUse_second_level_cache(String use_second_level_cache) {
		this.use_second_level_cache = use_second_level_cache;
	}

	public String getUse_query_cache() {
		return use_query_cache;
	}

	public void setUse_query_cache(String use_query_cache) {
		this.use_query_cache = use_query_cache;
	}

	public String getUse_sql_comments() {
		return use_sql_comments;
	}

	public void setUse_sql_comments(String use_sql_comments) {
		this.use_sql_comments = use_sql_comments;
	}

	public String getDefault_batch_fetch_size() {
		return default_batch_fetch_size;
	}

	public void setDefault_batch_fetch_size(String default_batch_fetch_size) {
		this.default_batch_fetch_size = default_batch_fetch_size;
	}

	public String getQuery_cache_factory() {
		return query_cache_factory;
	}

	public void setQuery_cache_factory(String query_cache_factory) {
		this.query_cache_factory = query_cache_factory;
	}

	public String getAuto_commit() {
		return auto_commit;
	}

	public void setAuto_commit(String auto_commit) {
		this.auto_commit = auto_commit;
	}

	public String getIsolation() {
		return isolation;
	}

	public void setIsolation(String isolation) {
		this.isolation = isolation;
	}

	public String getJdbc_batch_size() {
		return jdbc_batch_size;
	}

	public void setJdbc_batch_size(String jdbc_batch_size) {
		this.jdbc_batch_size = jdbc_batch_size;
	}
}
