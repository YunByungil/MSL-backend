package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.service.AuthService;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<TokenDto> login(@RequestBody UserLoginRequestDto requestDto) throws IllegalAccessException {
//        return ResponseEntity.ok(authService.login(requestDto));
//    }
    @PostMapping("/sign")
    public ResponseEntity join(@RequestBody UserJoinRequestDto userJoinDto){
        if (userService.joinDuplicate(userJoinDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(userJoinDto);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
}
