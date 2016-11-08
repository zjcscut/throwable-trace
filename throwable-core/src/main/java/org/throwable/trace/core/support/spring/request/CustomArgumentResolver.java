package org.throwable.trace.core.support.spring.request;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.throwable.trace.core.support.spring.request.annotation.CustomParam;

import java.lang.reflect.Field;



/**
 * @author zhangjinci
 * @version 2016/10/31 12:09
 * @function 自定义复杂参数接收处理器
 */
@Component
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String SEPARATOR = ".";

    @Autowired
    private ConversionService conversionService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CustomParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (webDataBinderFactory == null) {
            return null;
        }

        Class<?> targetType = methodParameter.getParameterType();
        CustomParam customParam = methodParameter.getParameterAnnotation(CustomParam.class);
        String prefix = getPrefix(customParam, targetType);
        Object target = BeanUtils.instantiate(targetType);
        WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, null, prefix);
        Object arg;
        Field[] fields = targetType.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String customParamName = buildCustomParamName(prefix, field.getName());
            if (ArrayUtils.contains(customParam.match(), field.getType())) { //使用Apache的数组包含判断
                arg = conversionService.convert(nativeWebRequest.getParameter(customParamName), field.getType());  //使用ConversionService里面的Converters进行转换
            } else {
                arg = binder.convertIfNecessary(nativeWebRequest.getParameter(customParamName), field.getType(), methodParameter);
            }
            field.set(target, arg);
        }
        return target;
    }

    private String buildCustomParamName(String prefix, String fieldName) {
        if (!StringUtils.isBlank(prefix)) {
            return prefix + SEPARATOR + fieldName;
        }
        return fieldName;
    }

    private String getPrefix(CustomParam customParam, Class<?> targetType) {
        String prefix = customParam.value();
        if (StringUtils.isBlank(prefix)) {
            prefix = getDefaultClassName(targetType);
        }
        return prefix;
    }

    private String getDefaultClassName(Class<?> targetType) {
        return ClassUtils.getShortNameAsProperty(targetType);
    }
}
