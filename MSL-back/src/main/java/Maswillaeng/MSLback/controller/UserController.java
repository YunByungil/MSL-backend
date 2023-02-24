package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResultResponse;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponse;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserLoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDTO;
import Maswillaeng.MSLback.dto.user.request.UserListDTO;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.CookieUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/user")
    public ResponseEntity<Object> member(Authentication authentication) {
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        UserListDTO userListDTO = new UserListDTO(user);
        return ResponseEntity.ok().body(userListDTO);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(Authentication authentication) {
        log.info("userId = {}", authentication.getName());
//        log.info("co = {}", co);
        return ResponseEntity.ok().body("님의 test");
    }

    @GetMapping("/test2")
    public String test2() {
        return "okTest2";
    }

    /**
     * login
     */
//    @GetMapping("/loginTest")
//    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto dto) {
//        // TODO: 토큰 정보 어떻게 뿌릴지?
//        //https://seob.dev/posts/%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80-%EC%BF%A0%ED%82%A4%EC%99%80-SameSite-%EC%86%8D%EC%84%B1/
//
//        UserLoginResponseDto userLoginResponseDto = userService.login(dto);
//
//        ResponseCookie accessToken = cookieUtil.createAccessCookieToken(userLoginResponseDto);
//
//        ResponseCookie refreshToken = cookieUtil.createRefreshCookieToken(userLoginResponseDto);
//
//        LoginResultResponse l = getLoginResultResponse(userLoginResponseDto);
//
//        return ResponseEntity.ok()
//                .header("Set-Cookie", accessToken.toString())
//                .header("Set-Cookie", refreshToken.toString())
//                .body(new LoginResponseDto(HttpStatus.OK.value(), l));
//    }

    private LoginResultResponse getLoginResultResponse(UserLoginResponseDto userLoginResponseDto) {
        LoginResultResponse l = LoginResultResponse
                .builder()
                .nickname(userLoginResponseDto.getNickname())
                .userImage(userLoginResponseDto.getUserImage())
                .build();
        return l;
    }

    @PostMapping("/sign")
    public Result join(@RequestBody UserJoinDTO userJoinDTO) {
        log.info("userJoinDTO = {}", userJoinDTO);
        userService.join(userJoinDTO);
        return new Result(HttpStatus.OK.value());
    }

    @GetMapping("/token")
    public ResponseEntity reissue(@CookieValue("refreshToken") String to, Authentication authentication) {
        System.out.println("\"gdgd\" = " + "gdgd");

        System.out.println("authentication = " + to.toString());
        TokenResponse token = userService.reissueAccessToken(to);

        ResponseCookie cookie = cookieUtil.createCookie(token.getAccessToken());
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("토큰 발급 완료");
    }

    /**
     * 로그아웃
     * 유저 정보 불러와서, 1. DB에 담겨있는 리프레쉬 토큰 삭제
     * 2. 헤더에 있는 쿠키 정보 삭제
     */
    @PostMapping("/logout")
    public ResponseEntity logout(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        System.out.println("userId1231231231 = " + userId);
        userService.deleteRefreshToken(userId);
        // TODO: 변수명 수정하기
        ResponseCookie cookie = cookieUtil.deleteAccessCookieToken();
        ResponseCookie cookie2 = cookieUtil.deleteRefreshCookieToken();
        System.out.println("\"\" = " + "실행되나염?");

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .header("Set-Cookie", cookie2.toString())
                .body("gd");
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T code;
    }
}
