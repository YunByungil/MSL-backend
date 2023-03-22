package com.maswilaeng.jwt.controller;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.duplicate.request.DuplicateRequestDto;
import com.maswilaeng.dto.user.request.LoginRequestDto;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.dto.user.response.LoginSuccessResponseDto;
import com.maswilaeng.jwt.dto.LoginResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static com.maswilaeng.jwt.entity.JwtTokenProvider.accessTokenExpireTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/api/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody DuplicateRequestDto dto) {

        if (authService.notDuplicateEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 이메일이 존재합니다.");
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody DuplicateRequestDto dto) {

        if (authService.notDuplicateNickName(dto.getNickName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임이 존재합니다.");
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/duplicate-phoneNumber")
    public ResponseEntity<Object> duplicatePhoneNumber(@RequestBody DuplicateRequestDto dto) {

        if (authService.notDuplicatePhoneNumber(dto.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 핸드폰 번호가 존재합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PostMapping("/api/sign")
    public ResponseEntity<Object> signup(@RequestBody UserJoinDto userJoinDto) throws Exception {
        User user = userJoinDto.toUser();

        if (authService.notDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.signup(userJoinDto);
        }

        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK));
    }


    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        LoginResponseDto dto = authService.login(loginRequestDto);

        long timeLeft = accessTokenExpireTime - (new Date()).getTime();
        long hours = timeLeft/1000 / 3600;
        long minutes = (timeLeft/1000 % 3600) / 60;
        long seconds = timeLeft/1000 % 60;

        String remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);


        LoginSuccessResponseDto loginSuccessResponseDto = LoginSuccessResponseDto.builder()
                .nickName(dto.getNickName())
                .userImage(dto.getUserImage())
                .accessTokenExpiresIn(remainingTime)
                .build();

        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                dto.getTokenResponseDto().getACCESS_TOKEN()
        );

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                dto.getTokenResponseDto().getREFRESH_TOKEN()
        );

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(ResponseDto.of(HttpStatus.OK, loginSuccessResponseDto));
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
