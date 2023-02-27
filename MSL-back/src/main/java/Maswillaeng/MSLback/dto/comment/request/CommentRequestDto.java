package Maswillaeng.MSLback.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long userId;

    private Long postId;

    private String content;

}

