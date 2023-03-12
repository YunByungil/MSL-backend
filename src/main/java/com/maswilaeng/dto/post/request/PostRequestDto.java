package com.maswilaeng.dto.post.request;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor

@Builder
public class PostRequestDto {

    private Long post_id;
    private Long user_id;
    private LocalDateTime createdAt;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;

    /* Dto -> Entity */
    public Post toEntity() {
        Post posts = Post.builder()
                .post_id(post_id)
                .user_id(user_id)
                .createdAt(createdAt)
                .thumbnail(thumbnail)
                .title(title)
                .content(content)
                .modifiedAt(modifiedAt)
                .build();

        return posts;
    }
}
