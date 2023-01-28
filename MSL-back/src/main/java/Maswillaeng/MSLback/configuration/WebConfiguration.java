package Maswillaeng.MSLback.configuration;

import Maswillaeng.MSLback.interceptor.JwtTokenInterceptor;
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
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = Arrays.asList("/join", "/duplicate", "/login");

        registry.addInterceptor(jwtTokenInterceptor()).excludePathPatterns(excludes);
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(tokenArgumentResolver);
//    }
}
