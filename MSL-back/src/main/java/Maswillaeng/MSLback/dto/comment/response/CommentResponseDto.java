package Maswillaeng.MSLback.dto.comment.response;

import Maswillaeng.MSLback.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
//@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private String nickname;
    private Long postId;
    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.postId = comment.getPost().getId();
        this.createAt = comment.getCreateAt();
    }
}
