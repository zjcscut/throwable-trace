package org.throwable.trace.bean.entity;

import org.throwable.trace.bean.common.BaseEntity;

import javax.persistence.*;

/**
 * @author zhangjinci
 * @version 2016/11/9 11:41
 * @function 请求日志信息
 */
@Entity
@Table(name = "TB_AT_REQUEST_LOG_INFO")
public class RequestLogInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CLASS_NAME")
    private String className;
    @Column(name = "METHOD_NAME")
    private String methodName;
    @Column(name = "CLIENT_IP")
    private String clientIp;
    @Column(name = "REQUEST_MEDTHOD")
    private String requestMedthod;
    @Column(name = "REQUEST_HEADER")
    private String requestHeader;
    @Column(name = "REFERER")
    private String referer;
    @Column(name = "URI")
    private String uri;
    @Column(name = "URL")
    private String url;
    @Column(name = "REQUEST_PARAMS")
    private String requestParams;
    @Column(name = "USER_AGENT")
    private String userAgent;
    @Column(name = "REQUEST_TIME")
    private Long requestTime;
    @Column(name = "RESPONSE_TIME")
    private Long responseTime;
    @Column(name = "COST_TIME")
    private Long costTime;

    private transient boolean print;
    private transient boolean persistence;

    public RequestLogInfo() {
    }

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

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public boolean isPersistence() {
        return persistence;
    }

    public void setPersistence(boolean persistence) {
        this.persistence = persistence;
    }
}
