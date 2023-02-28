package com.maswilaeng.jwt.controller;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.user.request.LoginRequestDto;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.jwt.dto.LoginResponseDto;
import com.maswilaeng.jwt.dto.TokenResponseDto;
import com.maswilaeng.jwt.dto.UserLoginResponseDto;
import com.maswilaeng.jwt.service.AuthService;
import com.maswilaeng.utils.AuthCheck;
import com.maswilaeng.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/signup")
    public ResponseEntity<Object> signup(@RequestBody UserJoinDto userJoinDto) throws Exception{
        User user = userJoinDto.toUser();
        if (authService.notDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.signup(userJoinDto);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                loginResponseDto.getTokenResponseDto()
                        .getACCESS_TOKEN());

        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                loginResponseDto.getTokenResponseDto()
                        .getREFRESH_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(new UserLoginResponseDto(loginResponseDto.getNickName(), loginResponseDto.getUserImage()));
    }

//    @PostMapping("/reissue")
//    public ResponseEntity<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
//        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
//    }

    @PostMapping("/api/logout")
    public ResponseEntity<Object> logout() {
        Long userId = UserContext.userData.get().getUserId();
        authService.removeRefreshToken(userId);
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0")
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0")
                .body(ResponseDto.of("로그아웃 되었습니다."));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/api/updateToken")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {
        TokenResponseDto token = authService.updateAccessToken(refreshToken);
        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=" + token.getACCESS_TOKEN()).build();
    }



}
