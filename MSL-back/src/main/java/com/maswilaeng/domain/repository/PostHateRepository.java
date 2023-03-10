package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.PostHate;
import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostHateRepository extends JpaRepository<PostHate, Long> {
    Optional<PostHate> findByUserAndPost(User user, Post post);
}
