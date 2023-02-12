package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserSignDto;
import Maswillaeng.MSLback.dto.user.request.UserUpateDto;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class UserController {
   @Autowired
   private final UserService userService;

    @Autowired
    private UserResponseDto userResponseDto;

    @Autowired
    private final UserRepository userRepository;

    /*
    1. 회원 가입
    memo :
    - findOne 으로 해도 될거같은데 좀 더 체크 필요
    - id가 없을테니 email로
    */
   @PostMapping("/sign")
    public HttpStatus sign (@RequestBody UserSignDto signReq ){
       User checkRegistration = userRepository.findByEmail(signReq.getEmail());
       if(checkRegistration == null ){
           userService.save(signReq);
       }else {
           return HttpStatus.BAD_REQUEST;
       }

       return HttpStatus.OK;
   }

    /*
    2. 내 정보 조회 -  member_id에 대한게 뭔지..잘 모르겠슴등? 일단 id 라는 가정하에 진행..
    */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> showMyInfo (@PathVariable("id") Long id){
        User user = userService.findIdUser(id);
        if(user == null ){
            new ResponseEntity<>(userResponseDto, HttpStatus.BAD_REQUEST);
        }else {
            userResponseDto =  userService.selectMyInfo(user);
        }
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /*
    3. 내 정보 수정 -  tokent으로 id를 받나? 일단 진행
    memo :
    - 중복 검사 대상 ( 이메일, 닉네임 끝? )
    */
    @PutMapping("/user/{id}")
    public HttpStatus updateMyInfo (@PathVariable("id") Long id, @RequestBody UserUpateDto upateDto ){
        userService.updateMyInfo(id ,upateDto);
        return HttpStatus.OK;
    }

    /*
    4. 탈퇴
    */
    @DeleteMapping("/user/{id}")
    public HttpStatus drawUser (@PathVariable("id") Long id ){
        User checkUser = userService.findIdUser(id);
        userService.drawUser(checkUser);
        return HttpStatus.OK;
    }
}
