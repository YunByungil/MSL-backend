package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.request.UserJoinDTO;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDTO userJoinDTO) throws IOException {
        User user = userJoinDTO.toEntity();
        System.out.println("user.getEmail() = " + user.getEmail());
        System.out.println("user.getRole() = " + user.getRole());
        System.out.println("user.getNickname() = " + user.getNickname());

        log.info("userJoinDTO = {}", userJoinDTO);
        userService.join(user);
        return ResponseEntity.ok().build();
    }

}
