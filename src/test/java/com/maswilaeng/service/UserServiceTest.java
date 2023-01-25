package com.maswilaeng.service;

import com.maswilaeng.Domain.entity.User;
import com.maswilaeng.Domain.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    MemoryUserRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }
    @AfterEach
    public void afterEach() {
        userRepository.clearStore();
    }

    @Test
    void join() {

        User user = new User();
        user.setNickname("user1");

        Long savedUser = userService.join(user);

        User findUser = userService.findOne(savedUser).get();
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());

    }

    @Test
    void 중복회원예외() {

        User user1 = new User();
        user1.setNickname("user1");

        User user2 = new User();
        user2.setNickname("user1");

        Long savedId = userService.join(user1);
        try{
            userService.join(user2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

    }
}