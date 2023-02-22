package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.request.LoginDto;
import Maswillaeng.MSLback.service.TokenService;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    /*
      1. 로그인
    */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.findIdUser(loginDto.getId());

        if(user != null && loginDto.getPw().equals(user.getPwd())){ //비밀번호 일치 확인
                String accessToken =
                        tokenService.generateToken(user.getId()
                                , new Date(System.currentTimeMillis() + ( 30 * 60 * 100)));

                String refreshToken =
                        tokenService.generateToken(user.getId()
                                , new Date(System.currentTimeMillis() + (6 * 60 * 60 * 100)));

                tokenService.setTokenCookies(
                        response
                        , "accessToken"
                        ,  accessToken
                        , 600 );

                tokenService.setTokenCookies(
                        response
                        , "refreshToken"
                        , refreshToken
                        , 21600 );

            user.setRefresh_token(refreshToken);
            userService.setRefreshToken(user);

            return ResponseEntity.ok("로그인 완료");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    /*
      2. accesstoken 만료시 재발급 :  프론트에서 체크를 어떤식으로 하는지 궁금함 (유효시간전에 재호출을 하나?)
    */

    @PostMapping("/reissuanceToken/{refreshToken}")
    public ResponseEntity<?> reissuanceToken(@PathVariable("refreshToken") String refreshToken , HttpServletResponse response){
       //claims 확인해서 refresh 유효하면 accesstoken 발급
        User user = userService.findIdUser(tokenService.parseJwt(refreshToken));

        // 유효하지 않으면 logOut!!!!
        // 유효하지 않으면 페이지를 이동해야하지 않나..? 메인으로?

        if(user != null && refreshToken.equals(user.getRefresh_token())) {
            String accessToken =
                    tokenService.generateToken(user.getId()
                            , new Date(System.currentTimeMillis() + (30 * 60 * 100)));

            tokenService.setTokenCookies(
                    response
                    , "accessToken"
                    , accessToken
                    , 600);

            return ResponseEntity.ok("토큰 재발급 완료");

        }else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refreshToken 이 만료되었습니다. 다시 로그인 하세요. ");
        }

    }


    @PostMapping("/logout/{accessToken}")
    public void logOut(@PathVariable("accessToken") String accessToken , HttpServletResponse response){
        User user = userService.findIdUser(tokenService.parseJwt(accessToken));
        if(user != null && accessToken.equals(user.getRefresh_token())) {

        }
    }


}
