package Maswillaeng.MSLback.Util;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = HeaderUtils.getAccessToken(request);

        tokenProvider.isValidAccessToken(accessToken);
        Long userId = tokenProvider.decode(accessToken);

        if (!tokenProvider.hasRefreshToken(userId)) {
            throw new AuthenticationException();
        }

        return true;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        //JwtToken 호출"
//        String accessToken = request.getHeader("ACCESS_TOKEN");
////        String refreshToken = request.getHeader("REFRESH_TOKEN");
//
//        if (accessToken != null && tokenProvider.isValidAccessToken(accessToken)) {
//            return true;
//        }
//
//        response.setStatus(401);
//        response.setHeader("ACCESS_TOKEN", accessToken);
////        response.setHeader("REFRESH_TOKEN", refreshToken);
//        response.setHeader("msg", "Check the tokens.");
//        return false;
//    }
}