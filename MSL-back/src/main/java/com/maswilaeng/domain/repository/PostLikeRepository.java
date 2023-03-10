package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.PostLike;
import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByUserAndPost(User user, Post post);

    boolean existsByUserAndPost(User user, Post post);
}
