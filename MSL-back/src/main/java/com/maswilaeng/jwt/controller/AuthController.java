package com.maswilaeng.jwt.controller;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.user.request.LoginRequestDto;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.dto.user.response.LoginSuccessResponseDto;
import com.maswilaeng.jwt.entity.TokenInfo;
import com.maswilaeng.jwt.service.AuthService;
import com.maswilaeng.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/api/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody String email) {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody String nickname) {
        if (userRepository.existsByNickName(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/sign")
    public ResponseEntity<Object> signup(@RequestBody UserJoinDto userJoinDto) throws Exception {
        User user = userJoinDto.toUser();
        log.info("회원가입 요청 들어온 user : {}", user.getEmail());

        if (authService.notDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.signup(userJoinDto);
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        TokenInfo tokenInfo = authService.login(loginRequestDto);

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("해당 이메일의 유저가 존재하지 않습니다.")
        );

        log.info("tokenInfo.getExpiresIn() : {}", tokenInfo.getExpiresIn());
        long remainingSeconds = tokenInfo.getExpiresIn() - ((new Date()).getTime());
        log.info("remainingSeconds : {}", remainingSeconds);


        long hours = remainingSeconds/1000 / 3600;
        long minutes = (remainingSeconds/1000 % 3600) / 60;
        long seconds = remainingSeconds/1000 % 60;

        String remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);


        LoginSuccessResponseDto dto = LoginSuccessResponseDto.builder()
                .nickName(user.getNickName())
                .userImage(user.getUserImage())
                .accessTokenExpiresIn(tokenInfo.getExpiresIn())
                .build();

        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                tokenInfo.getAccessToken()
        );

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                tokenInfo.getRefreshToken()
        );


        HashMap<String, Object> result = new HashMap<>();
        result.put("nickName", user.getNickName());
        result.put("userImage", user.getUserImage());
        result.put("remainTime", remainingTime);

        HashMap<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.OK.value());
        response.put("result", result);

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(response);
    }



    @PostMapping("/api/logout")
    public ResponseEntity<Object> logout() {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        authService.removeRefreshToken(currentUserId);

        return ResponseEntity.ok()
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0;")
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0;")
                .body(ResponseDto.of(
                        "로그아웃 성공"
                ));
    }

//    @PostMapping("/reissue")
//    public ResponseEntity<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
//        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
//    }


//    @AuthCheck(role = AuthCheck.Role.USER)
//    @GetMapping("/api/updateToken")
//    public ResponseEntity<Object> updateAccessToken(@CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {
//        TokenResponseDto token = authService.updateAccessToken(refreshToken);
//        return ResponseEntity.ok()
//                .header("Set-Cookie", "ACCESS_TOKEN=" + token.getACCESS_TOKEN()).build();
//    }

}
