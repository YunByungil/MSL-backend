package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 지정한 유저가 쓴 글만 불러오는 메서드 (미완성)
     */
    List<Post> findByUserId(Long userId);
}
