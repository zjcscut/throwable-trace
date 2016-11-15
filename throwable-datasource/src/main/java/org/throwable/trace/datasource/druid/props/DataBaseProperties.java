package org.throwable.trace.datasource.druid.props;

/**
 * @author zjc
 * @version 2016/11/12 18:05
 * @description 数据库配置
 */
public class DataBaseProperties {

	/**
	 * 是否主数据源
	 */
	private Boolean is_master;
	/**
	 * 数据源名字,用于切换数据源
	 */
	private String name;
	/**
	 * 数据库连接
	 */
	private String url;
	/**
	 * 驱动class名称
	 */
	private String driver;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;

	public Boolean getIs_master() {
		return is_master;
	}

	public void setIs_master(Boolean is_master) {
		this.is_master = is_master;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
