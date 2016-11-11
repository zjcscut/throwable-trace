package org.throwable.trace.datasource.druid.props;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.throwable.trace.bean.vo.DataSource;

import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/11/11 17:07
 * @function
 */
@Component
@ConditionalOnProperty(prefix = "org.throwable.trace.datasource.config")
public class DataSourceProperties {


}
