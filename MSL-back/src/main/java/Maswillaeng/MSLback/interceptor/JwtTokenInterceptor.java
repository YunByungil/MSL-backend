package Maswillaeng.MSLback.interceptor;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ComponentScan
@NoArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("jwt 토근 생성");
        String accessToken = new String();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies)
        {
            if (cookie.getName().equals("ACCESS_TOKEN"))
                accessToken=  cookie.getValue();
        }
//        String accessToken = "ACCESS_TOKEN".getValue();
//        String accessToken =request.getHeader("ACCESS_TOKEN");
        System.out.println("accessToKEN : "+accessToken);
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        System.out.println("refreshToken : "  +refreshToken);


        if(refreshToken !=null){
            if(jwtTokenProvider.isValidRefreshToken(refreshToken))
                return true;
            else
                System.out.println("301");
            response.sendRedirect("/login");
        }


        if (accessToken != null && jwtTokenProvider.isValidAccessToken(accessToken)) {
            //  Claims userClaim =  jwtTokenProvider.getAccessClaims(accessToken);

            //   request.setAttribute("userClaim",userClaim);
            System.out.println("controller 진입");
            return true;
        }

        //401을 보내주면 refresh 토큰과 함께 /updateAccessToken controller불러오기
        response.setStatus(401);

        return false;
    }
}
