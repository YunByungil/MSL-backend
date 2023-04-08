package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.comment.request.CommentReplyRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentSaveRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public void saveComment(Long userId, CommentSaveRequestDto requestDto){
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new EntityNotFoundException("Post not found")
        );
        User user = userRepository.findById(userId).get();

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
    }

    public void saveReply(Long userId, CommentReplyRequestDto requestDto){
        Comment comment = commentRepository.findById(requestDto.getParentId())
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다. id=" + requestDto.getParentId()));
        User user = userRepository.findById(userId).get();

        Comment reply = Comment.builder()
                .post(comment.getPost())
                .user(user)
                .content(requestDto.getContent())
                .parent(comment)
                .build();
        commentRepository.save(reply);
    }

    public List<Comment> findAllComments(){
        List<Comment> commentList = commentRepository.findAll();
        return commentList;
    }

    public List<CommentResponseDto> findAllReplies(Long parentId){
        List<Comment> replyList = commentRepository.findByParentId(parentId);
        return replyList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<Comment> findCommentByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다. id=" + userId));
        List<Comment> commentList = user.getCommentList();
        return commentList;
    }

    public Comment findCommentByCommentId(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다. id=" + commentId));
        return comment;
    }

    public void updateComment(Long userId, CommentUpdateRequestDto requestDto) throws AccessDeniedException {
        Comment comment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다. id=" + requestDto.getCommentId()));
        validateUser(userId, comment);
        comment.updateComment(requestDto.getContent());
    }

    public void deleteComment(Long userId, Long commentId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다. id=" + commentId));
        validateUser(userId, comment);
        commentRepository.delete(comment);
    }

    public void validateUser(Long userId, Comment comment) throws AccessDeniedException {
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다");
        }
    }
}
