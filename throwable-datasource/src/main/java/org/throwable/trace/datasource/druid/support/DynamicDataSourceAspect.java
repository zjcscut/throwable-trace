package org.throwable.trace.datasource.druid.support;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.throwable.trace.core.datasource.DataSourceContext;
import org.throwable.trace.core.datasource.DataSourceContextHolder;
import org.throwable.trace.core.utils.extend.Assert;
import org.throwable.trace.datasource.druid.support.annotation.TargetDataSource;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangjinci
 * @version 2016/10/12 18:07
 * @function 数据源切换切面函数
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

	//全局数据源上下文容器
	public static final ConcurrentHashMap<String, DataSourceContext> dataSourceContext = new ConcurrentHashMap<>();

	//匹配所有被@TargetDataSource标注的执行中的方法
	@Pointcut("@annotation(org.throwable.trace.datasource.druid.support.annotation.TargetDataSource)")
	public void point() {
	}

	@Before("point()")
	public void setDataSourceContext(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); //获取方法签名
		if (methodSignature != null) {
			Method method = methodSignature.getMethod();
			if (method != null && method.isAnnotationPresent(TargetDataSource.class)) {
				TargetDataSource targetDataSource = method.getDeclaredAnnotation(TargetDataSource.class);
				Assert.notNull(targetDataSource);
				Assert.notBlank(targetDataSource.value(), "target dynamic datasouce name must not be blank");
				DataSourceContextHolder.setDataSourceContext(dataSourceContext.get(targetDataSource.value()));
			}
		}
	}


}
