package Maswillaeng.MSLback.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        //JwtToken 호출"
        String accessToken = request.getHeader("ACCESS_TOKEN");
//        String refreshToken = request.getHeader("REFRESH_TOKEN");

        if (accessToken != null && tokenProvider.isValidAccessToken(accessToken)) {
            return true;
        }

        response.setStatus(401);
        response.setHeader("ACCESS_TOKEN", accessToken);
//        response.setHeader("REFRESH_TOKEN", refreshToken);
        response.setHeader("msg", "Check the tokens.");
        return false;
    }
}