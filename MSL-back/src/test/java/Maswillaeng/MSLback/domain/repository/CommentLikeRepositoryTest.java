package Maswillaeng.MSLback.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentLikeRepositoryTest {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach


    @DisplayName("commentLikeRepository가 null인지 확인")
    @Test
    void isNotNull() {
        // given
        Assertions.assertThat(commentLikeRepository).isNotNull();
        // when

        // then
    }

    @DisplayName("댓글 좋아요 등록")
    @Test
    void likeComment() {
        // given
        userRepository.findById(1L);

        // when

        // then
    }
}