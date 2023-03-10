package com.maswilaeng.dto.post.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeRequestDto {

    private Long postId;

    public PostLikeRequestDto(Long postId) {
        this.postId = postId;
    }
}
