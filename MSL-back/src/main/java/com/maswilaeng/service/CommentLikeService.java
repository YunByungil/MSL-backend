package com.maswilaeng.service;

import com.maswilaeng.domain.entity.*;
import com.maswilaeng.domain.repository.*;
import com.maswilaeng.dto.comment.request.CommentLikeRequestDto;
import com.maswilaeng.utils.SecurityUtil;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CommentLikeService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentHateRepository commentHateRepository;

    public void insertCommentLike(CommentLikeRequestDto commentLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Comment comment = commentRepository.findById(commentLikeRequestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        //이미 좋아요 되어 있으면 에러 반환
        if (commentLikeRepository.findByUserAndComment(user, comment).isPresent()) {
            throw new RuntimeException("이미 해당 회원은 좋아요를 눌렀습니다.");
        }

        //싫어요가 있을시 없애주기
        if (commentLikeRepository.findByUserAndComment(user, comment).isPresent()) {
            commentRepository.subHateCount(comment.getId());
            CommentHate commentHate = commentHateRepository.findByUserAndComment(user, comment).get();
            commentHateRepository.delete(commentHate);
        }

        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        commentLikeRepository.save(commentLike);
        commentRepository.addLikeCount(comment.getId());
    }

    public void deleteCommentLike(CommentLikeRequestDto commentLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Comment comment = commentRepository.findById(commentLikeRequestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        // 좋아요가 눌려있지 않을 시
        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 좋아요 정보입니다.")
        );

        commentRepository.subLikeCount(comment.getId());
        commentLikeRepository.delete(commentLike);
    }

    public void insertCommentHate(CommentLikeRequestDto commentLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Comment comment = commentRepository.findById(commentLikeRequestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        //이미 싫어요 되어 있으면 에러 반환
        if (commentHateRepository.findByUserAndComment(user, comment).isPresent()) {
            throw new RuntimeException("이미 해당 회원은 싫어요를 눌렀습니다.");
        }

        //좋아요가 있을시 없애주기
        if (commentLikeRepository.findByUserAndComment(user, comment).isPresent()) {
            commentRepository.subLikeCount(comment.getId());
            CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment).get();
            commentLikeRepository.delete(commentLike);
        }

        CommentHate commentHate = CommentHate.builder()
                .comment(comment)
                .user(user)
                .build();

        commentHateRepository.save(commentHate);
        commentRepository.addHateCount(comment.getId());
    }

    public void deleteCommentHate(CommentLikeRequestDto commentLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );
        Comment comment = commentRepository.findById(commentLikeRequestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        // 싫어요가 눌려있지 않을 시
        CommentHate commentHate = commentHateRepository.findByUserAndComment(user, comment).orElseThrow(
                () -> new EntityNotFoundException("싫어요가 눌려있지 않습니다.")
        );

        commentRepository.subHateCount(comment.getId());
        commentHateRepository.delete(commentHate);
    }
}
