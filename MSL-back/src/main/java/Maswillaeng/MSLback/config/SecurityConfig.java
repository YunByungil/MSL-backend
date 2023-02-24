package Maswillaeng.MSLback.config;

import Maswillaeng.MSLback.auth.LoginFilter;
import Maswillaeng.MSLback.auth.PrincipalDetailsService;
import Maswillaeng.MSLback.common.exception.CustomAccessDeniedHandler;
import Maswillaeng.MSLback.common.exception.CustomAuthenticationEntryPoint;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final JwtFilter jwtFilter;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/api/post/1", "/api", "/api/token");
//    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("SecurityFilterChain 진입 완료");

        return http.httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // exception 토큰
//                .authenticationEntryPoint((request, response, authException) ->
//                        response.sendRedirect("/api/token"))
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // exception 권한
                .and()
                .authorizeRequests()
                .antMatchers("/api/sign", "/api/login", "/api/token", "/api/logout", "/home").permitAll()
                .antMatchers("/api/**")
                .access("hasRole('ROLE_USER')")
                .anyRequest().permitAll()
                .and()
                .apply(new MyCustomDsl())
                .and()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            System.out.println("체크포인트!@@!@!@!@");
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(new LoginFilter(authenticationManager));
//                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }
}
