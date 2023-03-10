package com.maswilaeng.dto.comment.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeRequestDto {

    private Long commentId;

    public CommentLikeRequestDto(Long commentId) {
        this.commentId = commentId;
    }
}