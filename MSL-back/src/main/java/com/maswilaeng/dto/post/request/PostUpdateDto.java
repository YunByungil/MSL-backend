package com.maswilaeng.dto.post.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class PostUpdateDto {

    private Long postId;

    private String thumbnail;

    private String title;

    @NotNull
    private String content;

    private Set<String> hashTagSet;
}
