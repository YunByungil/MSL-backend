//package com.maswilaeng.config;
//
//import com.maswilaeng.jwt.JwtFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<> filter1() {
//        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>(new JwtFilter());
//        bean.addUrlPatterns("/*");  // 모든 요청에 대해서 필터 적용
//        bean.setOrder(0);   // 낮은 숫자일수록 우선순위
//
//        return bean;
//    }
//}
