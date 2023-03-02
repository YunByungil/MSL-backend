package com.maswilaeng.dto.post.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateDto {

    private Long Id;

    private String thumbnail;

    private String title;

    @NotNull
    private String content;
}
