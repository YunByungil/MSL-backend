package com.maswilaeng.Domain.repository;

import com.maswilaeng.Domain.entity.Post;
import com.maswilaeng.Domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    Post findByUser(User user);
}
