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
    public ResponseEntity<Object> join(@RequestBody UserForm form){

        // 회원가입 API /sign 에서 원하는 정보를 body로 전달하기 위한 form
        User user = new User();

        user.setUser_id(form.getUser_id());
        user.setEmail(form.getEmail());
        user.setPw(form.getPw());
        user.setNickName(form.getNickName());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setUserImage(form.getUserImage());
        user.setIntroduction(form.getIntroduction());
        user.setWithdraw_yn(form.getWithdraw_yn());
        user.setRole(form.getRole());
        user.setRefresh_token(form.getRefresh_token());
        user.setCreated_at(form.getCreated_at());
        user.setModified_at(form.getModified_at());
        user.setWithdraw_at(form.getWithdraw_at());

        userService.join(user);

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
