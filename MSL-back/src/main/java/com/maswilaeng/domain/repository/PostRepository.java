package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.dto.post.response.PostResponseDto;
import com.maswilaeng.dto.post.response.UserPostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<PostResponseDto> findAllPostByUserId(Long userId);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    Post findByUser(User user);

    Optional<Post> findById(Long Id);

    Long findUserIdById(Long Id);

    Page<Post> findByUserId(@Param("userId") Long userId, PageRequest createdAt);
}
