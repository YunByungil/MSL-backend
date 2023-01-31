package Maswillaeng.MSLback.controller;


import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
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

    @GetMapping("/updateToken")
    public ResponseEntity updateAccessToken(@CookieValue("ACCESS_TOKEN")String accessToken, @CookieValue("REFRESH_TOKEN")String refreshToken) throws Exception {
        return ResponseEntity.ok().body(userService.updateAccessToken(accessToken,refreshToken));
    }

    @GetMapping("/user")
    public ResponseEntity getUser(@CookieValue("ACCESS_TOKEN")String userToken){

        return ResponseEntity.ok().body(userService.getUser(userToken));
    }

    @PutMapping("/user")
    public ResponseEntity updatedUser(@CookieValue("ACCESS_TOKEN")String userToken,@RequestBody UserUpdateRequestDto requestDto){
        if(requestDto.getPassword() == null && requestDto.getNickName()==null){
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(userToken,requestDto);
        return ResponseEntity.ok().build();
    }

    //post
    @GetMapping("user/postList")
    public ResponseEntity userPostList(@CookieValue("ACCESS_TOKEN")String userToken,@PageableDefault(sort = "createdAt",direction =  Sort.Direction.DESC) Pageable pageable) throws Exception {

        return ResponseEntity.ok().body(userService.userPostList(userToken,pageable));
    }

    @DeleteMapping("user")
    public ResponseEntity userWithDraw(@CookieValue("ACCESS_TOKEN")String userToken){
        userService.userWithDraw(userToken);
        return ResponseEntity.ok().build();


    }



}
