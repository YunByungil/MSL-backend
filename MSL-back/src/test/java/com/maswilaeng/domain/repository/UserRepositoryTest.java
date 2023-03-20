package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.jwt.controller.AuthController;
import com.maswilaeng.jwt.service.AuthService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

//@AutoConfigureMockMvc
//@SpringBootTest
//class UserRepositoryTest {
//
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private AuthController authController;
//
//    @Rollback(false)
//    @Test
//    void 회원가입() throws Exception {
//        //given
//        User user = new User("test@test.com",
//                "1234", "gilbert",
//                "010-3119-6598", "1234.jpeg",
//                "안녕하세요 gilbert입니다.");
//
//        //when
//        ResponseEntity<Object> signup = authController.signup(new UserJoinDto(user));
//
//        assertThat(signup.getStatusCode().is2xxSuccessful());
//
//    }
//}