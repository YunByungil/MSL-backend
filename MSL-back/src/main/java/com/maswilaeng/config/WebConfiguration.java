package com.maswilaeng.config;

import com.maswilaeng.utils.AuthInterceptor;
import com.maswilaeng.utils.ValidInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ValidInterceptor validInterceptor() {
        return new ValidInterceptor();}
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validInterceptor());
        registry.addInterceptor(authInterceptor());
    }



}