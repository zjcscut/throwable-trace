package org.throwable.trace.datasource;

/**
 * @author zhangjinci
 * @version 2016/11/8 11:55
 * @function 数据源上下文管理(当前只有一个上下文)
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<DataSourceContext> contextHolder = new ThreadLocal<>();

    public static void setDataSourceContext(DataSourceContext dataSourceContext) {
        contextHolder.set(dataSourceContext);
    }

    public static DataSourceContext getDataSourceContext() {
        return contextHolder.get();
    }

    public static void removeDataSourceContext() {
        contextHolder.remove();
    }
}
