package Maswillaeng.MSLback.auth;

import Maswillaeng.MSLback.domain.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter  extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /*
    /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("attemptAuthentication 함수 실행: 로그인 시도 중");
        try {


            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            log.info("user = {}",  user);

            // PrincipalDetailsService의 loadUsrByUsername() 함수가 실행된 후 정상이면 authentication이 리턴 됨
            // Db에 있는 username과 password가 일치한다.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()); // 토큰 생성
            log.info("user.getUsername() = {}", user.getEmail());
            log.info("user.getPassword() = {}", user.getPassword());

            // authentication 객체가 session 영역에 저장 됨 => 로그인이 되었다는 뜻.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("로그인 완료됨: {}", principalDetails.getUser().getEmail()); // 로그인 정상 완료

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    /login 성공했을 때,
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    /*
        /login 실패했을 때,
         */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("unsuccessfulAuthentication 함수 실행: 로그인 실패!!!!");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
