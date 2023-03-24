package Maswillaeng.MSLback.Util;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class DummyDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public DummyDataInitializer(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .email("user1@test.com")
                .password("password1")
                .nickname("user1")
                .phoneNumber("010-1111-1111")
                .userImage("user1.jpg")
                .introduction("Hello, I am user1!")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .email("user2@test.com")
                .password("password2")
                .nickname("user2")
                .phoneNumber("010-2222-2222")
                .userImage("user2.jpg")
                .introduction("Hello, I am user2!")
                .build();
        userRepository.save(user2);

        Post post1 = Post.builder()
                .title("Post 1 title")
                .content("Post 1 content")
                .thumbnail("post1.jpg")
                .user(user1)
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("Post 2 title")
                .content("Post 2 content")
                .thumbnail("post2.jpg")
                .user(user2)
                .build();
        postRepository.save(post2);

    }
}