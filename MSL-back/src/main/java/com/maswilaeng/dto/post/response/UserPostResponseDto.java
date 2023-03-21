package com.maswilaeng.dto.post.response;

import com.maswilaeng.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostResponseDto {
    private Long postId;

    public UserPostResponseDto(Post post) {
        postId = post.getId();
    }}
