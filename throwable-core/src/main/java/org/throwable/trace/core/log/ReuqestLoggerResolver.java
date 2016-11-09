package org.throwable.trace.core.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.throwable.trace.bean.entity.RequestLogInfo;
import org.throwable.trace.core.log.annotation.RquestLogger;
import org.throwable.trace.core.utils.date.XDateUtils;
import org.throwable.trace.core.utils.extend.AopUtils;
import org.throwable.trace.core.utils.extend.ReflectionUtils;
import org.throwable.trace.core.utils.http.RequestUtil;
import org.throwable.trace.core.utils.json.FastJsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
        StringBuilder desc = new StringBuilder();
        //获取当前执行的方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method m = methodSignature.getMethod();
        String methodFullName = AopUtils.getMethodAndParamsName(pjp);
        RquestLogger clazzAnno = ReflectionUtils.getAnnotation(target, RquestLogger.class);
        RquestLogger methodAnno = ReflectionUtils.getAnnotation(m, RquestLogger.class);
        if (clazzAnno != null) {
            desc.append(clazzAnno.value()).append(":").append(clazzAnno.description());
        }
        if (methodAnno != null) {
            if (clazzAnno != null) {
                desc.append("-");
            }
            desc.append(methodAnno.value()).append(":").append(methodAnno.description());
        }
        Map<String, Object> params = new LinkedHashMap<>();
        String[] paramNames = methodSignature.getParameterNames();
        Object[] args = pjp.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            Object arg = args[i];
            String argString = "";
            if (arg instanceof HttpServletRequest
                    || arg instanceof HttpSession
                    || arg instanceof HttpServletResponse
                    || arg instanceof MultipartFile
                    || arg instanceof MultipartFile[]) {
                continue;
            }
            if (arg instanceof String) argString = arg.toString();
            else if (arg instanceof Number) argString = String.valueOf(arg);
            else if (arg instanceof Date) argString = XDateUtils.format((Date) arg, XDateUtils.LONG_DATETIME_PATTERN);
            else {
                try {
                    argString = FastJsonUtils.toJson(arg);
                } catch (Exception e) {
                    log.error("cast <{}> to json String failed,message:{}", arg.getClass().getTypeName(), e.getMessage());
                }
            }
            params.put(paramNames[i], argString);
        }
        Map<String, String> headers = RequestUtil.getHeaders(request);
        requestLogInfo.setDescription(desc.toString());
        requestLogInfo.setClassName(target.getName());
        requestLogInfo.setMethodName(methodFullName);
        requestLogInfo.setRequestMedthod(request.getMethod());
        requestLogInfo.setRequestHeader(FastJsonUtils.toJson(headers));
        requestLogInfo.setClientIp(RequestUtil.getIpAddr(request));
        requestLogInfo.setReferer(headers.get("Referer"));
        requestLogInfo.setUrl(request.getRequestURL().toString());
        requestLogInfo.setUri(request.getRequestURI());
        requestLogInfo.setRequestParams(FastJsonUtils.toJson(params));
        return requestLogInfo;
    }
}
