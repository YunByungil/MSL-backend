package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.dto.comment.request.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    /**
     * 댓글 작성
     * @Param postId, userId, content, parent
     * 댓글에 유저, 포스트 정보를 다 넣어야 함.
     */
    public void addComment(CommentDto commentDto) {
        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);
    }
}
