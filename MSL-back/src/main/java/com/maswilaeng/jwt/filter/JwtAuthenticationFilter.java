package com.maswilaeng.jwt.filter;

import com.maswilaeng.config.CustomUserDetailsService;
import com.maswilaeng.jwt.entity.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("JwtAuthenticationFilter의 doFilter 호출");
        // 1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        log.info("JwtAuthenticationFilter의 token : {}", token);
        log.info("JwtAuthenticationFilter의 jwtTokenProvider.validateToken(token) : {}", jwtTokenProvider.validateToken(token));

        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        log.info("JwtAuthenticationFilter의 doFilter 마지막 단계 옴");
        chain.doFilter(request, response);

    }

    //Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessCookie = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ACCESS_TOKEN")) {
                accessCookie = cookie.getValue();
                log.info("JwtAuthenticationFilter의 resolveToken 실행 중 : cookie.getValue() :{}", accessCookie);
                return accessCookie;
            }
        }
        return null;
    }
}

