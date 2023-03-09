package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.CommentHate;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.CommentHateRepository;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentHateService {

    private final CommentHateRepository commentHateRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void hateComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글이 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다."));
        Optional<CommentHate> findCommentHate = commentHateRepository.findByCommentIdAndUserId(commentId, userId);
        if (findCommentHate.isPresent()) {
            throw new IllegalStateException("해당 댓글에 이미 싫어요를 누르셨습니다.");
        }
        CommentHate commentHate = CommentHate.builder()
                .user(user)
                .comment(comment)
                .build();
        commentHateRepository.save(commentHate);
    }

    public void unHateComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글이 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다."));

        CommentHate commentHate = commentHateRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글에 싫어요를 누르지 않았습니다."));

        commentHateRepository.delete(commentHate);
    }
}
