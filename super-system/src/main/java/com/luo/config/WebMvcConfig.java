//package com.luo.config;
//
//import com.luo.common.DateConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//
////@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new DateConverter());
//    }
//
////    @Bean
//    public ExecutorService executorService() {
//        return Executors.newCachedThreadPool();
//    }
//}
