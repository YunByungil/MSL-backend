package Maswillaeng.MSLback.controller;


import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/duplicate")
    public ResponseEntity duplicate(@RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "nickName", required = false) String nickName){
        if(email ==null && nickName==null) {
            return ResponseEntity.badRequest().build();
        }

        if((email !=null && userService.existsByEmail(email))
                ||(nickName !=null &&userService.existsBynickName(nickName)))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody User user) {
        if(userService.joinDuplicate(user))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            userService.join(user);
        return  ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto request) throws Exception {
        LoginResponseDto dto = userService.login(request);
//        Cookie token = new Cookie("ACCESS_TOKEN",dto.getACCESS_TOKEN());
        ResponseCookie responseCookie = ResponseCookie.from("ACCESS_TOKEN",dto.getTokenResponse().getACCESS_TOKEN())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
        HashMap<String,String> user = new HashMap<>();
        user.put("nickName",dto.getNickName());
        user.put("userImage", dto.getUserImage());
        return ResponseEntity.ok().header("Set-Cookie",responseCookie.toString()).body(user);
    }

//    @GetMapping("/test")
//    public String test(@TokenRequest String token){
//        System.out.println("token 확인 :" + token );
//        return "success";
//    }

    @GetMapping("/updateToken")
    public ResponseEntity updateAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(userService.updateAccessToken(request.getHeader("REFRESH_TOKEN"),request.getHeader("ACCESS_TOKEN")));
    }

    @GetMapping("/user")
    public ResponseEntity getUser(@CookieValue("ACCESS_TOKEN")String userToken){

        return ResponseEntity.ok().body(userService.getUser(userToken));
    }



}
