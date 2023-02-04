package Maswillaeng.MSLback.configuration;


import Maswillaeng.MSLback.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

   // private final TokenArgumentResolver tokenArgumentResolver;

    @Bean
    public AuthInterceptor jwtTokenInterceptor() {
        return new AuthInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
       // List<String> excludes = Arrays.asList("/join", "/duplicate");

        registry.addInterceptor(jwtTokenInterceptor());
    }

}
