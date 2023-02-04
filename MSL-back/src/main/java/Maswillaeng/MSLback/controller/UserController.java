package Maswillaeng.MSLback.controller;


import Maswillaeng.MSLback.annotation.AuthCheck;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.UserContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity join(@RequestBody UserJoinRequestDto user) {
        if(userService.joinDuplicate(user))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            userService.join(user);
        return  ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto request, HttpServletResponse response) throws Exception {
        LoginResponseDto dto = userService.login(request);

        Cookie accessToken = new Cookie("ACCESS_TOKEN", dto.getTokenResponse().getACCESS_TOKEN());
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");

        Cookie refreshToken = new Cookie("REFRESH_TOKEN", dto.getTokenResponse().getREFRESH_TOKEN());
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/updateToken");

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        HashMap<String,String> user = new HashMap<>();
        user.put("nickName",dto.getNickName());
        user.put("userImage", dto.getUserImage());
        return ResponseEntity.ok().body(user);
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/updateToken")
    public ResponseEntity updateAccessToken(@CookieValue("ACCESS_TOKEN")String accessToken, @CookieValue("REFRESH_TOKEN")String refreshToken) throws Exception {
        System.out.println(refreshToken);
        ResponseEntity ss = ResponseEntity.ok().body(userService.updateAccessToken(accessToken,refreshToken));
        return ss;
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/user")
    public ResponseEntity getUser(){

        return ResponseEntity.ok().body(userService.getUser(UserContext.userId.get()));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/user")
    public ResponseEntity updatedUser(@RequestBody UserUpdateRequestDto requestDto){
        if(requestDto.getPassword() == null && requestDto.getNickName()==null){
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(UserContext.userId.get(),requestDto);
        return ResponseEntity.ok().build();
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("user/postList")
    public ResponseEntity userPostList(@PageableDefault(sort = "createdAt",direction =  Sort.Direction.DESC,size=20) Pageable pageable) throws Exception {

        return ResponseEntity.ok().body(userService.userPostList(UserContext.userId.get(),pageable));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("user")
    public ResponseEntity userWithDraw(){
        userService.userWithDraw(UserContext.userId.get());
        return ResponseEntity.ok().build();


    }



}
