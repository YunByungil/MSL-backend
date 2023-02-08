package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/duplicate")
    public ResponseEntity duplicate(@RequestParam(value = "email") String email, @RequestParam(value = "nickName") String nickName){
        if(email == null || nickName == null) {
            return ResponseEntity.badRequest().build();
        }

        if(userRepository.existsByEmail(email) || userRepository.existsByNickName(nickName))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            return ResponseEntity.ok().build();
    }

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinRequestDto userJoinDto) {
        User user = userJoinDto.toEntity();
        if (userService.duplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            userService.join(user);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestParam Long id, @RequestBody UserUpdateRequestDto requestDto){
        userService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

}
