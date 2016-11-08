package org.throwable.trace.core.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.throwable.trace.core.security.xss.XssFilter;

/**
 * @author zhangjinci
 * @version 2016/11/8 20:38
 * @function 安全性配置
 */
@Configuration
public class SecuritySupportConfiguration {

    @Bean(name = "XssFilter")
    public FilterRegistrationBean XssFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new XssFilter());
        filterRegistrationBean.setName("XssFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
