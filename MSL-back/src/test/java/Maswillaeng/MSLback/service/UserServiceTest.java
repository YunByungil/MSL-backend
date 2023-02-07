package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserRepository userRepository;

    @Test
    void 회원가입() {
        // given
        User user = new User(0L, "test@test", "test1", "bang", "010-1234-1234",
                null, "hi", "n", "ahffk", null, null);
        // when
        userService.join(user);

        // then

    }
}