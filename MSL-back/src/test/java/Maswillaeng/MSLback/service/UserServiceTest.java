package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserRepository userRepository;

    @Test
    void 회원가입() {
        // given
//        User user = createUser();
        User user = User.builder()
                .email("test@test")
                .password("test1")
                .nickname("bang")
                .userImage("dsa")
                .introduction("hi")
                .phoneNumber("010-1234-1234")
                .refresh_token("dsa")
                .role("ahffk")
                .withdrawAt(LocalDateTime.now())
                .build();
        // @ColumnDefault 수정해야 됨!!
        // when
        Long saveId = userService.join(user);

        User findUser = userService.findOne(saveId);
        // then
        Assertions.assertThat(saveId).isEqualTo(findUser.getId());
        System.out.println("findUser.getWithdrawYn() = " + findUser.getWithdrawYn());

    }

    @Test
    void 중복_회원() {
        // given
        createUser();

        createUser();
        // when

        // then
    }

    private static User createUser() {
        User user = User.builder()
                .email("test@test")
                .password("test1")
                .nickname("bang")
                .userImage("dsa")
                .introduction("hi")
                .phoneNumber("010-1234-1234")
                .refresh_token("dsa")
                .role("ahffk")
                .withdrawAt(LocalDateTime.now())
                .build();
        return user;
    }
}