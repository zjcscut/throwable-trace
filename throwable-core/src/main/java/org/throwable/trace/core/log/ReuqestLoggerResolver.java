package org.throwable.trace.core.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.throwable.trace.bean.entity.RequestLogInfo;
import org.throwable.trace.core.log.annotation.RquestLogger;
import org.throwable.trace.core.utils.extend.AopUtils;
import org.throwable.trace.core.utils.extend.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhangjinci
 * @version 2016/11/9 11:49
 * @function 请求日志处理器
 */
public class ReuqestLoggerResolver {

    private static final Logger log = LoggerFactory.getLogger(ReuqestLoggerResolver.class);

    public RequestLogInfo resolve(ProceedingJoinPoint pjp, HttpServletRequest request) {
        RequestLogInfo requestLogInfo = new RequestLogInfo();
        log.debug("access request logger resolver ..");
        Class<?> target = pjp.getTarget().getClass();
        //获取当前执行的方法
        Method m = ((MethodSignature) pjp.getSignature()).getMethod();
        String methodFullName = AopUtils.getMethodAndParamsName(pjp);
        RquestLogger clazzAnno = ReflectionUtils.getAnnotation(target, RquestLogger.class);
        RquestLogger methodAnno = ReflectionUtils.getAnnotation(m, RquestLogger.class);


        return requestLogInfo;
    }
}
