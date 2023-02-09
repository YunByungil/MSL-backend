package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.request.UserJoinDTO;
import Maswillaeng.MSLback.dto.user.request.UserListDTO;
import Maswillaeng.MSLback.dto.user.request.UserUpdateDTO;
import Maswillaeng.MSLback.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 모든 회원 조회
     */
    @GetMapping("/test")
    public Result memberList() {
        List<User> users = userService.findAll();
        List<UserListDTO> collect = users.stream()
                .map(u -> new UserListDTO(u))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDTO userJoinDTO) {
        User user = userJoinDTO.toEntity();

        log.info("userJoinDTO = {}", userJoinDTO);
        userService.join(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user") // 로그인 구현x
    public ResponseEntity<Object> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        userService.update(1L, userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
//        private ResponseEntity<T> responseEntity;
        private T result;
    }
}
