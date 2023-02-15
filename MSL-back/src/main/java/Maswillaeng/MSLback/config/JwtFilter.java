package Maswillaeng.MSLback.config;

import Maswillaeng.MSLback.common.exception.ErrorCode;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static Maswillaeng.MSLback.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Value("${jwt.secret}")
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO: PrincipalDetails 구현
        log.info("=== doFilterInternal ===");
        log.info("SecretKey : {}", secretKey);

        /* 쿠키에서 토큰 꺼내기 */

        Cookie[] cookies = request.getCookies();

        log.info("SecretKey2 : {}", secretKey);
        if (cookies == null) {
            log.error("토큰이 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("SecretKey3 : {}", secretKey);
        String accessCookie = "";
        String refreshCookie = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                accessCookie = cookie.getValue();
            } else if (cookie.getName().equals("refreshToken")) {
                refreshCookie = cookie.getValue();
            }
        }

        log.info("SecretKey4 : {}", secretKey);
        if (!(refreshCookie == null || refreshCookie.equals(""))) { // 리프레쉬 토큰이 존재하면
            log.info("리프레시 토큰 존재한다");
            log.info("refreshCookie = {}", refreshCookie);
            exceptionRefresh(request, response, filterChain, refreshCookie);
        } else if (!(accessCookie == null || accessCookie.equals(""))) {
            log.info("엑세스 토큰 존재한다");
            exceptionAccess(request, response, filterChain, accessCookie);
        }
        log.info("SecretKey5 : {}", secretKey);

//        if (!(accessCookie == null || accessCookie.equals(""))) { // 엑세스 토큰이 존재하면
//            log.info("엑세스 토큰 존재한다");
//            exceptionAccess(request, response, filterChain, accessCookie);
//        }

        /*
        1. 필터에서 쿠키를 체크한다.
        2. 리프레쉬 토큰이 존재할 때,
         2-1. 리프레쉬 토큰의 유효기간
         2-2. 리프레쉬 토큰이 제대로 된 토큰인지
        3. 엑세스 토큰이 존재할 때,
         3-1. 엑세스 토큰의 유효기간
         3-2. 엑세스 토큰이 제대로 된 토큰인지
         */
//        if (!(refreshCookie == null || refreshCookie.equals(""))) {
//            try {
//                JwtUtil.isExpired(refreshCookie, secretKey);
//            } catch (ExpiredJwtException e) {
//                log.info("리프레쉬 토큰 유효기간 만료되었음!!");
//                request.setAttribute("exception", INVALID_TOKEN.name());
////                response.sendError(HttpStatus.UNAUTHORIZED.value());
////                filterChain.doFilter(request, response);
//            } catch (Exception e) {
//                log.info("error");
//                return;
//            }
//        }

    }

    private void exceptionRefresh(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String refreshCookie) throws ServletException, IOException {
        // TODO: 아직 수정해야 됨 PrinciplaDetails를 구현해야 된다. (권한, login)
        try {
            Long userId = JwtUtil.getUserId(refreshCookie, secretKey);
            System.out.println("userId111 = " + userId);
            // 권한 부여
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            System.out.println("userId111 = " + userId);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            System.out.println("userId111 = " + userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("userId111 = " + userId);

        } catch (SecurityException | MalformedJwtException | ExpiredJwtException e) {
            log.info("리프레쉬 토큰 유효기간 만료되었음!!");
            request.setAttribute("exception", INVALID_TOKEN.name());
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            log.info("권한이 없는데용?");
            request.setAttribute("exception", INVALID_PERMISSION.name());
        } catch (Exception e) {
            log.info("알 수 없는 오류~!~!");
            request.setAttribute("exception", UNKNOWN_ERROR.name());
        } finally {
            filterChain.doFilter(request, response);
        }

    }

    private void exceptionAccess(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String accessCookie) throws ServletException, IOException {
        // TODO: 아직 수정해야 됨 PrinciplaDetails를 구현해야 된다. (권한, login)
        try {
            Long userId = JwtUtil.getUserId(accessCookie, secretKey);
            System.out.println("userId222 = " + userId);
            // 권한 부여
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            System.out.println("userId222 = " + userId);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            System.out.println("userId222 = " + userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("userId222 = " + userId);

        } catch (ExpiredJwtException e) {
            log.info("엑세스 토큰 유효기간 만료되었음!!");
            request.setAttribute("exception", INVALID_TOKEN.name());
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            log.info("권한이 없는데용?");
            request.setAttribute("exception", INVALID_PERMISSION.name());
        } finally {
            filterChain.doFilter(request, response);
        }
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("=== doFilterInternal ===");
//
//        /* 헤더에서 토큰 꺼내기 */
//        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
////        Cookie[] cookies = request.getCookies();
////        for (Cookie cookie : cookies) {
////            System.out.println("cookie = " + cookie.getName());
////        }
//        log.info("Access 토큰 정보 : {}", authorization);
//        log.info("SecretKey : {}", secretKey);
//
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            log.error("토큰이 존재하지 않습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorization.split(" ")[1];
//
//        if (JwtUtil.isExpired(token, secretKey)) {
//            log.error("토큰 유효기간 만료");
//            return;
//        }
//
//        Long userId = JwtUtil.getUserId(token, secretKey);
//
//        // 권한 부여
//        UsernamePasswordAuthenticationToken authentication
//                = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));
//
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
////        response.addHeader("Authorization", authorization);
////        System.out.println("\"\" = " + "ㅇㄴㅁㅇㅁㄴㅇㅁㄴㄱ");
//        filterChain.doFilter(request, response);
//
//    }


}
