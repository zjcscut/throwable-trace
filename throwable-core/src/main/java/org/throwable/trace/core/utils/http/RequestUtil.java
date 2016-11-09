package org.throwable.trace.core.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Request 工具类
 *
 * @author zjcscut
 * @version 2016-6-26
 */
public final class RequestUtil {

    //重载一次这个方法
    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        return getRequestParams(request, null);
    }

    public static Map<String, Object> getRequestParams(HttpServletRequest request, String excludePrefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration<String> paramNames = request.getParameterNames();
        TreeMap<String, Object> params = new TreeMap<>();
        if (StringUtils.isBlank(excludePrefix)) {
            excludePrefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String unprefixed = null;
            if ("".equals(excludePrefix)) {
                unprefixed = paramName.substring(excludePrefix.length());
            } else if (paramName.startsWith(excludePrefix)) {
                unprefixed = paramName.substring(excludePrefix.length() + 1);
            }
            String values = Arrays.toString(request.getParameterValues(paramName));
            if (!values.isEmpty()) {
                params.put(unprefixed, values.substring(1, values.length() - 1));
            }
        }
        return params;
    }

    //获取所有请求头
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取请求客户端的真实ip地址
     *
     * @param request 请求对象
     * @return ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(" x-forwarded-for ");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取参数
     *
     * @param request
     * @param name    参数名
     * @return
     */
    public static String getString(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    /**
     * 获取参数
     *
     * @param request
     * @param name    参数名
     * @param def     默认值
     * @return
     */
    public static String getString(HttpServletRequest request, String name, String def) {
        String val = getString(request, name);
        return val == null ? def : val;
    }

    public static Integer getInt(HttpServletRequest request, String name) {
        String valueStr = getString(request, name);

        try {
            return Integer.valueOf(valueStr);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public static Integer getInt(HttpServletRequest request, String name, Integer def) {
        Integer val = getInt(request, name);
        return val == null ? def : val;
    }

    public static Float getFloat(HttpServletRequest request, String name) {
        String valueStr = getString(request, name);

        try {
            return Float.valueOf(valueStr);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public static Float getFloat(HttpServletRequest request, String name, Float def) {
        Float val = getFloat(request, name);
        return val == null ? def : val;
    }

    public static Double getDouble(HttpServletRequest request, String name) {
        String valueStr = getString(request, name);

        try {
            return Double.valueOf(valueStr);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public static Double getDouble(HttpServletRequest request, String name, Double def) {
        Double val = getDouble(request, name);
        return val == null ? def : val;
    }

    /**
     * 获取Request属性
     *
     * @param request
     * @param name
     */
    public static Object getRequestVal(HttpServletRequest request, String name) {
        return request.getAttribute(name);
    }

    /**
     * 设置Request属性
     *
     * @param request
     * @param name
     * @param value
     */
    public static void setRequestVal(HttpServletRequest request, String name, Object value) {
        request.setAttribute(name, value);
    }

    /**
     * 获取Session属性
     *
     * @param request
     * @param name
     * @return
     */
    public static Object getSessionVal(HttpServletRequest request, String name) {
        return request.getSession().getAttribute(name);
    }

    /**
     * 设置Session属性值
     *
     * @param request
     * @param name
     * @param value
     */
    public static void setSessionVal(HttpServletRequest request, String name, Object value) {
        request.getSession().setAttribute(name, value);
    }

}