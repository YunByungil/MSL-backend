//package Maswillaeng.MSLback.Util;
//
//import Maswillaeng.MSLback.domain.entity.Category;
//import Maswillaeng.MSLback.domain.entity.Post;
//import Maswillaeng.MSLback.domain.entity.RoleType;
//import Maswillaeng.MSLback.domain.entity.User;
//import Maswillaeng.MSLback.domain.repository.PostRepository;
//import Maswillaeng.MSLback.domain.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class DummyDataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//
//    public DummyDataInitializer(UserRepository userRepository, PostRepository postRepository) {
//        this.userRepository = userRepository;
//        this.postRepository = postRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        List<User> userList = new ArrayList<>();
//        for(int i = 1; i <= 50; i++) {
//            String email = "user" + i + "@test.com";
//            String password = "password" + i;
//            String nickname = "user" + i;
//            String phoneNumber = "010-" + i + i + i + i + "-" + i + i + i + i;
//            String userImage = "/upload_img/964929d0-7006-4ee5-a914-15faef6dd3f7_186bac28f874c1ca8.jpeg";
//            String introduction = "Hello, I am user" + i + "!";
//
//            User user = User.builder()
//                    .email(email)
//                    .password(password)
//                    .nickname(nickname)
//                    .phoneNumber(phoneNumber)
//                    .userImage(userImage)
//                    .introduction(introduction)
//                    .build();
//            user.updateRole(RoleType.USER);
//            userList.add(user);
//        }
//
//
//        for (int i = 1; i <= 300; i++) {
//            int j = i;
//            String title = "Post " + j + " title";
//            String content = "Post " + j + " content";
//            String thumbnail = "/upload_img/964929d0-7006-4ee5-a914-15faef6dd3f7_186bac28f874c1ca8.jpeg";
//            Category category = Category.values()[j%3];
//
//            if (j>50){
//                j = j/10;
//            }
//            User user = userRepository.findById((long)j).get();
//
//            Post post = Post.builder()
//                    .title(title)
//                    .content(content)
//                    .thumbnail(thumbnail)
//                    .user(user)
//                    .category(category)
//                    .build();
//
//            postRepository.save(post);
//        }
//    }
//}