package com.maswilaeng.dto.post.response;

import com.maswilaeng.domain.entity.HashTag;
import com.maswilaeng.domain.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class PostListResponseDto {

    private Long postId;

    private String nickname;

    private String title;

    private LocalDateTime createdAt;

    private Set<String> hashTagSet;

    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.nickname = post.getUser().getNickName();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.hashTagSet = post.getHashTagSet().stream()
                .map(hashTag -> hashTag.getTag().getTagName())
                .collect(Collectors.toSet());
    }
}
