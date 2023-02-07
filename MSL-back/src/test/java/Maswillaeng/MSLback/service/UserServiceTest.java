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
        User user = new User(1L, "test@test", "test1", "bang", "010-1234-1234",
                "dsa", "hi", "n", "ahffk", "dsa", LocalDateTime.now());
        // when
        Long saveId = userService.join(user);

        User findUser = userService.findOne(saveId);
        // then
        Assertions.assertThat(saveId).isEqualTo(findUser.getId());

    }
}