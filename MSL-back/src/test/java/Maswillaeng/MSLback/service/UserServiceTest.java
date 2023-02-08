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
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        User user = createUser();
        // @ColumnDefault 수정해야 됨!!
        // when
        Long saveId = userService.join(user);

        User findUser = userService.findOne(saveId);
        // then
        assertThat(saveId).isEqualTo(findUser.getId());
        System.out.println("findUser.getWithdrawYn() = " + findUser.getWithdrawYn());

    }

    @Test
    void 중복_회원() {
        // given
        User user1 = createUser();

        User user2 = createUser2();

        // when
        userService.join(user1);
//        System.out.println("user1.getEmail() = " + user1.getEmail());
//        System.out.println("user2.getEmail() = " + user2.getEmail());

        // then
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> userService.join(user2));
        assertEquals("이미 존재하는 Email입니다.", ex.getMessage());
        System.out.println("ex.getMessage() = " + ex.getMessage());
//        assertThatThrownBy(() -> userService.join(user2))
//                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> {
            userService.join(user2);
        }).isInstanceOf(IllegalStateException.class);

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
    private static User createUser2() {
        User user2 = User.builder()
                .email("test@t32est")
                .password("123")
                .nickname("6787")
                .userImage("dsa")
                .introduction("hi")
                .phoneNumber("010-444-1234")
                .refresh_token("dsa")
                .role("ahffk")
                .withdrawAt(LocalDateTime.now())
                .build();
        return user2;
    }
}