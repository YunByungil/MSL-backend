package Maswillaeng.MSLback.Util;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
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

        List<User> userList = new ArrayList<>();
        for(int i = 1; i <= 50; i++) {
            String email = "user" + i + "@test.com";
            String password = "password" + i;
            String nickname = "user" + i;
            String phoneNumber = "010-" + i + i + i + i + "-" + i + i + i + i;
            String userImage = "user" + i + ".jpg";
            String introduction = "Hello, I am user" + i + "!";

            User user = User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .phoneNumber(phoneNumber)
                    .userImage(userImage)
                    .introduction(introduction)
                    .build();
            user.updateRole(RoleType.USER);
            userList.add(user);
        }

        userRepository.saveAll(userList);


        for (int i = 1; i <= 300; i++) {
            int j = i;
            String title = "Post " + j + " title";
            String content = "Post " + j + " content";
            String thumbnail = "post" + j + ".jpg";

            if (j>50){
                j = j/10;
            }
            User user = userRepository.findById((long)j).orElse(null);

            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .thumbnail(thumbnail)
                    .user(user)
                    .build();

            postRepository.save(post);
        }
    }
}