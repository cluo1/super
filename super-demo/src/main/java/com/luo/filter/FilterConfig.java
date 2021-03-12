package com.luo.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Order(2)
public class FilterConfig {

    @Bean
    public FilterRegistrationBean parmsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/user/*", "/receiveView/*", "/serviceAnaly/*");
        //本地测试用 api
        //registration.addUrlPatterns("/api/user/*");

        // 排除不需要过滤的请求
        Set<String> set = new HashSet<>();
        set.add("/user/login");
        set.add("/user/logout");
        set.add("/api/user/login");
        set.add("/api/user/logout");
        set.add("/doc.html");
        String urls = StringUtils.join(set.toArray(), ",");
        registration.addInitParameter("exclusions", urls);
        registration.setName("loginFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }
}
