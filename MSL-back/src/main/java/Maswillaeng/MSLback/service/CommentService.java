package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.comment.request.CommentReplyRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public void saveComment(Long postId, CommentRequestDto requestDto){
        Long userId = requestDto.getUser().getId();

        Post post = postRepository.findById(requestDto.getPost().getId()).orElseThrow(
                () -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId)
        );
        User user = userRepository.findById(requestDto.getUser().getId()).orElseThrow(
                () -> new EntityNotFoundException("유저이 존재하지 않습니다. id=" + userId)
        );

        Comment comment = requestDto.toEntity(post, user);
    }

    public void saveReply(Long postId, Long commentId, CommentReplyRequestDto requestDto){
        Long userId = requestDto.getUser().getId();

        Post post = postRepository.findById(requestDto.getPost().getId()).orElseThrow(
                () -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId)
        );
        User user = userRepository.findById(requestDto.getUser().getId()).orElseThrow(
                () -> new EntityNotFoundException("유저이 존재하지 않습니다. id=" + userId)
        );
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다. id=" + commentId));

        Comment comment = requestDto.toEntity(post, user, parentComment);
        commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        List<Comment> commentList = commentRepository.findAll();
        return commentList;
    }

    public List<Comment> getCommentByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다. id=" + userId));
        List<Comment> commentList = user.getCommentList();
        return commentList;
    }

    public Comment getCommentByCommentId(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다. id=" + commentId));
        return comment;
    }

    public void updateComment(Long postId, Long commentId, CommentUpdateRequestDto requestDto){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("게시물이 존재하지 않습니다. id=" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다. id=" + commentId));
        comment.update(requestDto.getContent());
    }

    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다. id=" + commentId));
        commentRepository.delete(comment);
    }
}
