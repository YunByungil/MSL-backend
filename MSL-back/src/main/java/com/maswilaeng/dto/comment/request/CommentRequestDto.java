package com.maswilaeng.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long postId;

    private String content;
}
