package com.luo.config;

import com.luo.component.ImportSelectDemo;
import com.luo.filter.SuperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//@Configuration
//@EnableWebMvc
@Import({ImportSelectDemo.class})
public class SuperConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean superFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new SuperFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
