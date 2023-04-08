package Maswillaeng.MSLback.dto.comment.response;

import Maswillaeng.MSLback.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createDate;

    private String nickname;
    private String userImage;

    private Long postId;


    @Builder
    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreatedDate();
        this.nickname = comment.getUser().getNickname();
        this.userImage = comment.getUser().getUserImage();
        this.postId = comment.getPost().getId();
    }
}