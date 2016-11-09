package org.throwable.trace.bean.entity;

import org.throwable.trace.bean.common.BaseEntity;

/**
 * @author zhangjinci
 * @version 2016/11/9 11:41
 * @function 请求日志信息
 */
public class RequestLogInfo extends BaseEntity {

    private Long id;
    private String description;
    private String className;
    private String methodName;
    private String clientIp;
    private String requestMedthod;
    private String requestHeader;
    private String referer;
    private String uri;
    private String url;
    private String requestParams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getRequestMedthod() {
        return requestMedthod;
    }

    public void setRequestMedthod(String requestMedthod) {
        this.requestMedthod = requestMedthod;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }
}
