package com.maswilaeng.controller;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.dto.user.request.UserUpdateRequestDto;
import com.maswilaeng.dto.user.response.UserInfoResponseDto;
import com.maswilaeng.jwt.service.AuthService;
import com.maswilaeng.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(userDetails.getUsername());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("/user 요청 들어옴 : authentication : {}", authentication);
        User user = userService.findOne(Long.valueOf(authentication.getName()))
                .orElseThrow(
                        () -> new UsernameNotFoundException("존재하지 않는 회원입니다.")
                );
        log.info("/user 요청 들어옴 : user : {}", user.getId());
        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(user);
        return ResponseEntity.ok().body(userInfoResponseDto);
    }

    @GetMapping("/authtest")
    public ResponseEntity<?> getAuth(Authentication authentication) {
        return ResponseEntity.ok().body(authentication);
    }

    @PutMapping("/user")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        if (requestDto.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  userService.findOne(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new UsernameNotFoundException("유효하지 않습니다."));

        userService.updateUser(user.getId(), requestDto);

        log.info("update 실행 : {} " , authentication.getName());
        log.info("update 실행 : {} ", user.getId());
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        log.info("user.getUsername = {} ", user.getEmail());
        log.info("user.getPassword = {} ", user.getPassword());
        log.info("update 실행 : authentication 바꾸기 전 {} " , authenticationToken);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        
        log.info("update 실행 : authentication 중 {} " , authenticate);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return ResponseEntity.ok().build();
    }
//
    @DeleteMapping("/user")
    public ResponseEntity<Object> userWithDraw() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.userWithdraw(Long.valueOf(authentication.getName()));
        return ResponseEntity.ok().build();
    }

}
