package com.maswilaeng.dto.post.response;

import com.maswilaeng.domain.entity.PostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostLikeResponseDto {

    private Long userId;

    private Long postId;


    public static PostLikeResponseDto of(PostLike postLike) {
        return PostLikeResponseDto.builder()
                .userId(postLike.getUser().getId())
                .postId(postLike.getPost().getId())
                .build();
    }
}
