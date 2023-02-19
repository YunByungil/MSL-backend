package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.TokenDto;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserLoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor // 이게 멀까
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

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

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }
    @PostMapping("/sign")
    public ResponseEntity join(@RequestBody UserJoinRequestDto userJoinDto){
         //toEntity를 여기서?? -> 두번 쓸 거니까.. 한번에 하자
        if (userService.joinDuplicate(userJoinDto)) { //존재 여부는 controller에서 정의해야한다.
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            userService.join(userJoinDto);
            return ResponseEntity.ok().build();
        }
    }
    @GetMapping("/userList")
    public ResponseEntity getUsers(@RequestBody User user){
        Optional<User> responseDto = userService.findByUserId(user.getId());
        return ResponseEntity.ok().build(); //dto로 반환해서 보내주기 .. 이걸 어따 써??? 구
    }

    @GetMapping
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id){
        Optional<User> user = userService.findByUserId(id);//null처리
        return ResponseEntity.ok(user);
    }



    @PutMapping
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteByUserId(@PathVariable Long id) {
        userService.deleteByUserId(id);
        return ResponseEntity.ok().build();
    }
}
