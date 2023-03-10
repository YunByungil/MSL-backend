package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

//    List<PostResponseDto> findPostByUserId(Long userId);

    List<Post> findByTitleContaining(String keyword);

    Post findByUser(User user);

    Optional<Post> findById(Long Id);

    Long findUserIdById(Long Id);

    Page<Post> findByUserId(@Param("userId") Long userId, PageRequest createdAt);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :post_id")
    void addLikeCount(@Param("post_id") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :post_id")
    void subLikeCount(@Param("post_id") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.hateCount = p.hateCount + 1 WHERE p.id = :post_id")
    void addHateCount(@Param("post_id") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.hateCount = p.hateCount - 1 WHERE p.id = :post_id")
    void subHateCount(@Param("post_id") Long postId);
}
