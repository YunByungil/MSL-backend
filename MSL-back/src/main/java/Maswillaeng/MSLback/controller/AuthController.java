package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.auth.response.UserTokenResponseDto;
import Maswillaeng.MSLback.dto.auth.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.auth.request.UserLoginRequestDto;
import Maswillaeng.MSLback.service.AuthService;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/sign")
    public ResponseEntity join(@RequestBody UserJoinRequestDto requestDto) throws Exception {
        System.out.println(requestDto.getPassword());
        if (authService.joinDuplicate(requestDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(requestDto);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto requestDto) throws Exception {
        UserTokenResponseDto responseDto = authService.login(requestDto);
        return ResponseEntity.ok()
                .header("Set-Cookie", responseDto.getAccessToken())
                .header("Set-Cookie", responseDto.getRefreshToken())//리프레시 토큰 경로
                .body(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

    //Access Token을 재발급 위한 api
    @PostMapping("/issue")
    public ResponseEntity issueAccessToken(HttpServletRequest request) throws Exception {
        UserTokenResponseDto responseDto = authService.issueAccessToken(request);
        return ResponseEntity.ok()
                .header("Set-Cookie", responseDto.getAccessToken())
                .header("Set-Cookie", responseDto.getRefreshToken())
                .body(responseDto);
    }

    @PostMapping("/duplicate/email")
    public ResponseEntity emailDuplicate(@RequestBody Map<String, String> email){
        if(email == null) {
            return ResponseEntity.badRequest().build();
        }

        if(authService.emailDuplicate(email.get("email"))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else{
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/duplicate/nickname")
    public ResponseEntity nicknameDuplicate(@RequestBody Map<String, String> nickname){
        if(nickname == null) {
            return ResponseEntity.badRequest().build();
        }
        if(authService.nicknameDuplicate(nickname.get("nickName"))) {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else{
            return ResponseEntity.ok().build();}
    }


}
