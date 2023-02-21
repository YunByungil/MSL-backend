package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 작성
     * @Param postId, userId, content, parent
     * 댓글에 유저, 포스트 정보를 다 넣어야 함.
     * Service를 사용하면 안 될까?
     */
    @Transactional
    public void addComment(Long postId, Long userId, CommentRequestDto commentRequestDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 존재하지 않습니다."));

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 유저가 존재하지 않습니다."));
        Comment comment = commentRequestDto.toEntity(findPost, findUser);
        commentRepository.save(comment);
    }

    /**
     * 댓글 수정
     * @param postId
     * @param userId
     * @param commentId
     * @param dto 댓글 수정폼 dto
     */

    @Transactional
    public void updateComment(Long postId, Long userId, Long commentId, CommentUpdateDto dto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글이 존재하지 않습니다."));

        Long findUserId = comment.getUser().getId();

        if (findUserId != userId) {
            throw new IllegalStateException("내가 쓴 댓글이 아닙니다.");
        }

        comment.updateComment(dto);
    }

    /**
     * 댓글 삭제
     * 게시글이 유효한지, 내가 작성한 댓글이 맞는지 검증
     * 결국 업데이트랑 댓글 등록이랑 똑같은 검증
     * TODO: 메서드로 만들자
     */
    @Transactional
    public void deleteComment(Long postId, Long userId, Long commentId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글이 존재하지 않습니다."));

        Long findUserId = comment.getUser().getId();

        if (findUserId != userId) {
            throw new IllegalStateException("내가 쓴 댓글이 아닙니다.");
        }

        commentRepository.delete(comment);
    }

}