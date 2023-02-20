package Maswillaeng.MSLback.configuration;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // json 서버 응답을 js에서 처리할 수 있게 해줌
        config.addAllowedOrigin("*"); // 모든 ip에 응답 허용
        config.addAllowedMethod("*"); // 모든 HTTP Method에 허용
        config.addAllowedHeader("*"); // 모든 HTTP HEADER에 허용
        source.registerCorsConfiguration("/api/**", config);
        return null;
    }
}
