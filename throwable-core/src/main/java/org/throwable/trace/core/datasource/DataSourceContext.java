package org.throwable.trace.core.datasource;

import org.throwable.trace.core.datasource.DataBaseType;

import javax.sql.DataSource;

/**
 * @author zhangjinci
 * @version 2016/11/8 15:31
 * @function 数据源上下文
 */
public class DataSourceContext {

	private String name = "default";
	private Boolean isMaster = false;
	private DataBaseType dataBaseType;
	private DataSource dataSource;

	public DataSourceContext() {
	}

	public DataSourceContext(String name, Boolean isMaster,
							 DataBaseType dataBaseType, DataSource dataSource) {
		this.name = name;
		this.isMaster = isMaster;
		this.dataBaseType = dataBaseType;
		this.dataSource = dataSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMaster() {
		return isMaster;
	}

	public void setMaster(Boolean master) {
		isMaster = master;
	}

	public DataBaseType getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(DataBaseType dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		String toString = "DataSourceContext--" + name;
		if (isMaster) {
			toString += "-master";
		}
		return toString;
	}
}
