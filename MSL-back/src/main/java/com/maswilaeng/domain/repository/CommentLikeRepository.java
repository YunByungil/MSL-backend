package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Comment;
import com.maswilaeng.domain.entity.CommentLike;
import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
