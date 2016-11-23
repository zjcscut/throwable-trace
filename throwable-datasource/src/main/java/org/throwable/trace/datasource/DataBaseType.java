package org.throwable.trace.datasource;

/**
 * @author zhangjinci
 * @version 2016/11/8 11:56
 * @function 数据库类型
 */
public enum DataBaseType {

	MYSQL("com.mysql.jdbc.Driver", "org.throwable.trace.datasource.DynamicDataSource", "SELECT 1"),
	ORACLE("oracle.jdbc.driver.OracleDriver", "org.throwable.trace.datasource.DynamicDataSource", "SELECT 1 FROM DUAL"),
	H2("org.h2.Driver", "org.throwable.trace.datasource.DynamicDataSource", "SELECT 1"),
	UNDEFINED(null, null, null);

	DataBaseType(String driverClassName, String dataSourceClassName, String testQuery) {
		this.driverClassName = driverClassName;
		this.dataSourceClassName = dataSourceClassName;
		this.testQuery = testQuery;
	}

	private String driverClassName;
	private String dataSourceClassName;
	private String testQuery;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getDataSourceClassName() {
		return dataSourceClassName;
	}

	public void setDataSourceClassName(String dataSourceClassName) {
		this.dataSourceClassName = dataSourceClassName;
	}

	public String getTestQuery() {
		return testQuery;
	}

	public void setTestQuery(String testQuery) {
		this.testQuery = testQuery;
	}

	public DataBaseType getTypeFromUrl(String url) {
		String prefix = "jdbc:";
		for (DataBaseType type : DataBaseType.values()) {
			if (url.startsWith(prefix + type.name().toLowerCase())) {
				return type;
			}
		}
		return UNDEFINED;
	}

}
