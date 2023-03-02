package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Comment;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.CommentRepository;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.comment.request.CommentRequestDto;
import com.maswilaeng.dto.comment.request.RecommentRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    //댓글 다는 기능
    public void generateComment(Long userId, CommentRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

        User user = userRepository.findById(userId).get();

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .build();
        commentRepository.save(comment);
    }

    /**
     * 댓글 업데이트
     * @param dto
     * @throws ValidationException
     * TODO -> UserContext 손보기
     */
//    public void updateComment(CommentUpdateRequestDto dto) throws ValidationException {
//        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(
//                () -> new EntityNotFoundException("존재하지 않는 댓글입니다.")
//        );
//
//        validateUser(UserContext.userData.get().getUserId(), comment);
//        comment.updateComment(dto.getContent());
//    }
//
//    public void deleteComment(Long commentId) throws ValidationException {
//        Comment comment = commentRepository.findById(commentId).orElseThrow(
//                () -> new EntityNotFoundException("존재하지 않는 댓글입니다.")
//        );
//        validateUser(UserContext.userData.get().getUserId(), comment);
//        commentRepository.delete(comment);
//    }
//
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
    public void generateRecomment(Long userId, RecommentRequestDto dto) {
        Comment parentComment = commentRepository.findById(dto.getParentId()).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다.")
        );
        User user = userRepository.findById(userId).get();
        Comment recomment = Comment.builder()
                .post(parentComment.getPost())
                .user(user)
                .content(dto.getContent())
                .parent(parentComment)
                .build();
        commentRepository.save(recomment);
    }
}
