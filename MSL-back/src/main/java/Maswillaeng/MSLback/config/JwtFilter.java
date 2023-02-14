package Maswillaeng.MSLback.config;

import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Value("${jwt.secret}")
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=== doFilterInternal ===");

        /* 헤더에서 토큰 꺼내기 */
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Access 토큰 정보 : {}", authorization);
        log.info("SecretKey : {}", secretKey);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("토큰이 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("토큰 유효기간 만료");
            return;
        }

        Long userId = JwtUtil.getUserId(token, secretKey);

        // 권한 부여
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }
}
