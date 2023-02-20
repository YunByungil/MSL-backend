package com.maswilaeng.controller;

import com.maswilaeng.domain.entity.Role;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/sign")
    public String createForm(){
        return "users/createUserForm";
    }

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto)throws Exception{

        User user = userJoinDto.toEntity();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteByUserId(@PathVariable Long id) {
        userService.deleteByUserId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public String list(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }
}
