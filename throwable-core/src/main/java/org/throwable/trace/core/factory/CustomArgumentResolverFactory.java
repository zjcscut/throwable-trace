package org.throwable.trace.core.factory;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.throwable.trace.core.support.spring.request.CustomArgumentResolver;
import org.throwable.trace.core.concurrency.set.ConcurrentHashSet;

import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/11/8 16:52
 * @function 自定义请求参数处理器工厂
 */
public class CustomArgumentResolverFactory {

    private static final ConcurrentHashSet<HandlerMethodArgumentResolver> resolvers = new ConcurrentHashSet<>();

    static {
        resolvers.add(new CustomArgumentResolver());
    }

    public static void addCustomArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        resolvers.forEach(argumentResolvers::add);
    }

}
