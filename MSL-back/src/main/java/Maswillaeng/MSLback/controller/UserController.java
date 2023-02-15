package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.user.request.UserJoinReqDTO;
import Maswillaeng.MSLback.dto.user.request.UserUpdateReqDTO;
import Maswillaeng.MSLback.service.UserSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private final UserSerivce userSerivce;

    public UserController(UserSerivce userSerivce) {
        this.userSerivce = userSerivce;
    }

    // 가입
    @PostMapping("/sign")
    public ResponseEntity<?> join(@RequestBody UserJoinReqDTO userJoinReqDTO) throws Exception {
        userSerivce.join(userJoinReqDTO);
        return ResponseEntity.ok().build();
    }

    // 이메일 중복 확인
    @GetMapping("/duplicate/user-email")
    public ResponseEntity<?> checkEmailDuplicate(@RequestParam String email) {
        if(email == null) {
            return ResponseEntity.badRequest().build();
        }
        if(userSerivce.checkEmailDuplicate(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // 닉네임 중복 확인
    @GetMapping("/duplicate/nickname")
    public ResponseEntity<?> checkNickNameDuplicate(@RequestParam String nickName) {
        if(nickName == null) {
            return ResponseEntity.badRequest().build();
        }
        if(userSerivce.checkNickNameDuplicate(nickName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // 회원 정보 수정
    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        if(userUpdateReqDTO.getPassword()==null || userUpdateReqDTO.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        userSerivce.updateUser(userId, userUpdateReqDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 조회
    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestParam Long userId) {
        userSerivce.getUser(userId);
        return ResponseEntity.ok().body(userSerivce.getUser(userId));
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<?> userWithDraw(@RequestParam Long userId) {
        userSerivce.userWithDraw(userId);
        return ResponseEntity.ok().build();
    }
}
