package Maswillaeng.MSLback.dto.comment.response;

import Maswillaeng.MSLback.domain.entity.Comment;

public class CommentResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private Long postId;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.postId = comment.getPost().getId();
    }
}