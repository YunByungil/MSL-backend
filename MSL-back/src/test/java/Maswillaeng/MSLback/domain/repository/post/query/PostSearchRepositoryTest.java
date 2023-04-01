//package Maswillaeng.MSLback.domain.repository.post.query;
//
//import Maswillaeng.MSLback.TestConfig;
//import Maswillaeng.MSLback.domain.entity.Post;
//import Maswillaeng.MSLback.domain.entity.User;
//import Maswillaeng.MSLback.domain.enums.Category;
//import Maswillaeng.MSLback.domain.repository.PostRepository;
//import Maswillaeng.MSLback.domain.repository.UserRepository;
//import Maswillaeng.MSLback.dto.post.request.PostSearchCondition;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.PageRequest;
//
//import javax.persistence.EntityManager;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Import(TestConfig.class)
////@SpringBootTest
//class PostSearchRepositoryTest {
//
//    @Autowired
//    EntityManager em;
//    @Autowired
//    PostSearchRepository postSearchRepository;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @DisplayName("작성자이름으로 검색 테스트")
//    @Test
//    void searchWriter() {
//        // given
//        User user = User.builder()
//                .email("user")
//                .nickname("nick")
//                .password("pass")
//                .phoneNumber("phone")
//                .build();
//        em.persist(user);
//        Post post = Post.builder()
//                .title("title")
//                .content("content1234")
//                .category(Category.FREE)
//                .build();
//        em.persist(post);
//        PostSearchCondition condition = new PostSearchCondition("nock", null, null, null, null);
//        postSearchRepository.search(condition, PageRequest.of(0, 10));
//        // when
//
//        // then
//    }
//
//}