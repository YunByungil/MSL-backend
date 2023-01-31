package Maswillaeng.MSLback.interceptor;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        String refreshToken = new String();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies)
        {
            switch (cookie.getName()){
                case "ACCESS_TOKEN":
                    accessToken=  cookie.getValue();
                    break;
                case "REFRESH_TOKEN":
                    refreshToken =cookie.getValue();
            }
        }

        if(refreshToken !=null && !refreshToken.equals("")){
            if(jwtTokenProvider.isValidRefreshToken(refreshToken))
                return true;
            else
                 response.sendRedirect("/login");
        }


        if (accessToken != null && jwtTokenProvider.isValidAccessToken(accessToken)) {
            return true;
        }

        //401을 보내주면 refresh 토큰과 함께 /updateAccessToken controller불러오기
        response.setStatus(401);

        return false;
    }
}
