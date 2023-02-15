package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.config.EncryptConfig;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserLoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDTO;
import Maswillaeng.MSLback.dto.user.request.UserListDTO;
import Maswillaeng.MSLback.dto.user.request.UserUpdateDTO;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.CookieUtil;
import Maswillaeng.MSLback.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final CookieUtil cookieUtil;


//    /**
//     * 모든 회원 조회
//     */
//    @GetMapping("/test")
//    public Result memberList() {
//        List<User> users = userService.findAll();
//        List<UserListDTO> collect = users.stream()
//                .map(u -> new UserListDTO(u))
//                .collect(Collectors.toList());
//        return new Result(collect);
//    }

    /**
     * 회원 조회할 때, 반환 값을 어떻게 해야될지,
     * res에는 분명 배열 형태인데 dto넘기는 건데 왜 배열이지?
     */
//    @GetMapping("/user")
//    public ResponseEntity<Object> member() {
//        User user = userService.findOne(1L);
//        UserListDTO userListDTO = new UserListDTO(user);
//        return ResponseEntity.ok().body(userListDTO);
//    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@CookieValue("accessToken") String co, Authentication authentication) {
        log.info("userId = {}", authentication.getName());
        log.info("co = {}", co);
        return ResponseEntity.ok().body(authentication.getName() + "님의 test");
    }

    /**
     * login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto dto) {
        // TODO: 토큰 정보 어떻게 뿌릴지?
        //https://seob.dev/posts/%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80-%EC%BF%A0%ED%82%A4%EC%99%80-SameSite-%EC%86%8D%EC%84%B1/

        UserLoginResponseDto userLoginResponseDto = userService.login(dto);

        ResponseCookie accessToken = ResponseCookie
                .from("accessToken", userLoginResponseDto.getTokenResponse().getAccessToken())
                .path("/")
                .httpOnly(true)
                .maxAge(JwtUtil.ACCESS_TOKEN_EXPIRE_TIME)
                .sameSite("Lax")
                .build();

        ResponseCookie refreshToken = ResponseCookie
                .from("refreshToken", userLoginResponseDto.getTokenResponse().getRefreshToken())
                .path("/")
                .httpOnly(true)
                .maxAge(JwtUtil.REFRESH_TOKEN_EXPIRE_TIME)
                .sameSite("Lax")
                .build();

//        return ResponseEntity.ok().body(userLoginResponseDto);
        return ResponseEntity.ok()
                .header("Set-Cookie", accessToken.toString())
                .header("Set-Cookie", refreshToken.toString())
                .body(new LoginResponseDto(userLoginResponseDto.getNickName(), userLoginResponseDto.getUserImage()));
    }

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDTO userJoinDTO) {
        log.info("userJoinDTO = {}", userJoinDTO);
        userService.join(userJoinDTO);
        return ResponseEntity.ok().body("회원 가입 완료!");
    }

//    @PutMapping("/user") // 로그인 구현x
//    public ResponseEntity<Object> update(@RequestBody UserUpdateDTO userUpdateDTO) {
//        userService.update(1L, userUpdateDTO);
//        return ResponseEntity.ok().build();
//    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
//        private ResponseEntity<T> responseEntity;
        private T result;
    }
}
