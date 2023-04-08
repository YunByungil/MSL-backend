package Maswillaeng.MSLback.config;

import Maswillaeng.MSLback.Util.AuthenticationArgumentResolver;
import Maswillaeng.MSLback.Util.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                .allowedHeaders("*")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowCredentials(true)
                .maxAge(3600);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtTokenInterceptor).excludePathPatterns("/api/**");
////                .addPathPatterns("/api/**")
////                .excludePathPatterns("/api/user/upload", "/api/auth/**", "/api/post/posts/**");
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = Paths.get("MSL-back/src/main/upload/img/");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+ path.toAbsolutePath()+"/");
    }
}
