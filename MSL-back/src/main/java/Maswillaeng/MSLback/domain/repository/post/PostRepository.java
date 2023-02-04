package Maswillaeng.MSLback.domain.repository.post;


import Maswillaeng.MSLback.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long>{

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAll(Pageable pageable);

    @Query(value = "SELECT p FROM Post p" +
            " JOIN FETCH p.user" +
            " WHERE p.user.user_id = :userId",
            countQuery = "SELECT count(p) FROM Post p WHERE p.user.user_id = :userId")
    Page<Post> findAllByUser(@Param("userId")Long userId, Pageable pageable);

    @Query(value = "SELECT p FROM Post p" +
            " JOIN FETCH p.user" +
            " WHERE p.postId= :postId")
    Optional<Post> findPostsBy(@Param("postId")Long userId);

}
