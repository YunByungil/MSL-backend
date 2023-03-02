package com.maswilaeng.jwt;

import com.maswilaeng.config.CustomUserDetailsService;
import com.maswilaeng.jwt.entity.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * filter를 통해 JWT토큰이 유효한지 검증하는 메서드
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenProvider TokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 실제 필터링 로직이라고 볼 수 있음
     * JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext에 저장하는 역할 수행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        Cookie[] cookies = request.getCookies();
        String accessToken = " ";

        log.info("쿠키에서 토큰 꺼냄 : {}", cookies);

        if (!ObjectUtils.isEmpty(cookies)) {
            log.info("쿠키 값 있음 -> 로그인 상태");

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ACCESS_TOKEN")) {
                    accessToken = cookie.getValue();
                    if (StringUtils.hasText(accessToken) && TokenProvider.validateToken(accessToken)) {
                       Authentication authentication = TokenProvider.getAuthentication(accessToken);
                       SecurityContextHolder.getContext().setAuthentication(authentication);
                       log.info(" 'doFilterInternal 작동' -> Security Context에 '{}'인증 정보를 저장했습니다.", authentication.getName());
                       log.info("doFilterInternal 이후 Security Context : {}", SecurityContextHolder.getContext());
                       filterChain.doFilter(request, response);
                    }
                }

            }
            } else {
                log.info("{} 에 대한 처리 건너뛰기");
                filterChain.doFilter(request, response);
        }








//        // 1. Request Header 에서 토큰 꺼내기
//        String jwt = resolveToken(request);
//        log.info("'doFilterInternal 작동' -> request Header에서 토큰 꺼냈음.");
//
//        // 2. validateToken으로 토큰 유효성 검사
//        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext에 저장
//        if (StringUtils.hasText(jwt) && TokenProvider.validateToken(jwt)) {
//            Authentication authentication = TokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.info(" 'doFilterInternal 작동' -> Security Context에 '{}'인증 정보를 저장했습니다.", authentication.getName());
//        }
//        else{
//            log.info("유효한 JWT 토큰이 없습니다." + "jwt : " + TokenProvider.validateToken(jwt) + "StringUtils :"+ StringUtils.hasText(jwt));
//        }
//        filterChain.doFilter(request, response);

    }

    /**
     * HTTP Request 헤더에서 토큰의 정보만 추출
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

//        log.info("JwtFilter의 resolve 실행중 bearerToken: {}", bearerToken);
        log.info("JwtFilter의 resolve 실행중 StringUtils.hasText(bearerToken): {}", StringUtils.hasText(bearerToken));
//        log.info("JwtFilter의 resolve 실행중 bearerToken.startsWith(\"Bearer \"){}", bearerToken.startsWith("Bearer "));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
