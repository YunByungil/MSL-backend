package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.auth.request.UserTokenRequestDto;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto requestDto) throws IllegalAccessException {
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
    @PostMapping("/sign")
    public ResponseEntity join(@RequestBody UserJoinRequestDto userJoinDto){
        if (userService.joinDuplicate(userJoinDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(userJoinDto);
            return ResponseEntity.ok().build();
        }
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

    @GetMapping("/duplicate/email")
    public ResponseEntity emailDuplicate(@RequestParam String email){
        if(email == null) {
            return ResponseEntity.badRequest().build();
        }

        if(userService.emailDuplicate(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else{
            return ResponseEntity.ok().build();}
    }

    @GetMapping("/duplicate/nickname")
    public ResponseEntity nicknameDuplicate(@RequestParam String nickname){
        if(nickname == null) {
            return ResponseEntity.badRequest().build();
        }
        if(userService.nicknameDuplicate(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else{
            return ResponseEntity.ok().build();}
    }


}
