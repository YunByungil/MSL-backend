package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Comment;
import com.maswilaeng.domain.entity.CommentHate;
import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentHateRepository extends JpaRepository<CommentHate, Long> {
    Optional<CommentHate> findByUserAndComment(User user, Comment comment);
}
