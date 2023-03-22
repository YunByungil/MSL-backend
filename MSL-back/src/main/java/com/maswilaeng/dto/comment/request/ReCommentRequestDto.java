package com.maswilaeng.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReCommentRequestDto {
    private Long parentId;
    private String content;

}
