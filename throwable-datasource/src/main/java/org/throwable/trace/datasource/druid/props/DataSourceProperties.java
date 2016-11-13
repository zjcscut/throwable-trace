package org.throwable.trace.datasource.druid.props;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.throwable.trace.core.datasource.DataBaseType;
import org.throwable.trace.core.datasource.DataSourceContext;
import org.throwable.trace.core.exception.DataSourceException;
import org.throwable.trace.datasource.druid.support.DynamicDataSourceAspect;

import java.util.*;

/**
 * @author zhangjinci
 * @version 2016/11/11 17:07
 * @function 数据源配置
 */
@Component
@ConditionalOnProperty(prefix = "org.throwable.trace")
public class DataSourceProperties {

	@Autowired
	private DruidBasicProperties druidBasicProperties;

	List<DataBaseProperties> datasources = new ArrayList<>(); //key 为 datasources 列表形式

	public List<DataBaseProperties> getDatasources() {
		return datasources;
	}

	public void setDatasources(List<DataBaseProperties> datasources) {
		this.datasources = datasources;
	}


	//构造动态数据源列表
	public Map<Object, Object> buildTargetDynamicDataSource() {
		if (datasources == null || datasources.isEmpty()) {
			throw new DataSourceException("no any datasouce config could be found");
		}
		Map<Object, Object> datasoucesMap = new LinkedHashMap<>(datasources.size());
		for (DataBaseProperties pro : datasources) {
			DruidDataSource druidDataSource = buildDruidDataSource(pro.getUrl(), pro.getDriver(), pro.getUsername(), pro.getPassword());
			DataSourceContext dataSourceContext = new DataSourceContext(pro.getName(), pro.getIs_master(),
					DataBaseType.MYSQL.getTypeFromUrl(pro.getUrl()), druidDataSource);
			datasoucesMap.put(dataSourceContext, druidDataSource);
			DynamicDataSourceAspect.dataSourceContext.putIfAbsent(pro.getName(), dataSourceContext);
		}
		return datasoucesMap;
	}

	//构造Druid数据源
	private DruidDataSource buildDruidDataSource(String url, String driverClassName, String userName, String password) {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(url);
		druidDataSource.setDriverClassName(driverClassName);
		druidDataSource.setUsername(userName);
		druidDataSource.setPassword(password);
		druidBasicProperties.buildBasicDruidConfiguration(druidDataSource);
		return druidDataSource;
	}
}
