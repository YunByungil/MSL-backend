package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Comment;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.CommentRepository;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.comment.request.CommentRequestDto;
import com.maswilaeng.dto.comment.request.CommentUpdateRequestDto;
import com.maswilaeng.dto.comment.request.ReCommentRequestDto;
import com.maswilaeng.dto.comment.response.CommentResponseDto;
import com.maswilaeng.dto.comment.response.ReCommentResponseDto;
import com.maswilaeng.utils.SecurityUtil;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    //댓글 다는 기능
    public CommentResponseDto createComment(CommentRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(
                () -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

        User user = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(
                        () -> new RuntimeException("로그인 유저 정보가 없습니다."));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .parent(null)
                .likeCount(0)
                .hateCount(0)
                .build();

        return CommentResponseDto.of(commentRepository.save(comment));

    }

    /**
     * 댓글 업데이트
     * @param dto
     * @throws ValidationException
     */
    public void updateComment(CommentUpdateRequestDto dto) throws ValidationException {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 댓글입니다.")
        );
        User user = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

        comment.updateComment(dto.getContent());
    }

    public void deleteComment(Long commentId) throws ValidationException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 댓글입니다.")
        );
        validateUser(SecurityUtil.getCurrentUserId(), comment);
        comment.findDeletableComment().ifPresentOrElse(commentRepository::delete, comment::delete);
    }

    /**
     * 권한체크
     * @param userId
     * @param comment
     * @throws ValidationException
     */
    public void validateUser(Long userId, Comment comment) throws ValidationException {
        if (!comment.getUser().getId().equals(userId)) {
            throw new ValidationException("권한이 없습니다");
        }
    }

    /**
     * ㄱ
     */
    public ReCommentResponseDto createReComment(ReCommentRequestDto dto) {
        Comment parent = commentRepository.findById(dto.getParentId()).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다.")
        );
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new RuntimeException("로그인한 유저가 아닙니다")
        );

        Comment reComment = Comment.builder()
                .post(parent.getPost())
                .user(user)
                .content(dto.getContent())
                .parent(parent)
                .likeCount(0)
                .hateCount(0)
                .build();

        return ReCommentResponseDto.of(commentRepository.save(reComment));
    }


    public List<CommentResponseDto> getComment(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("게시물이 존재하지 않습니다"));

        List<Comment> commentList = commentRepository.findAllByPostId(post.getId());
        if (commentList.isEmpty()) {
            return Collections.emptyList();
        }

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(CommentResponseDto.of(comment));
        }

        return commentResponseDtoList;
    }

}
