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
        return ResponseEntity.ok().body(responseDto);
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
}
