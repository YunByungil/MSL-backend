package com.maswilaeng.dto.post.request;

import com.maswilaeng.Domain.entity.Post;
import com.maswilaeng.Domain.entity.User;
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
    private LocalDateTime created_at;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime modified_at;

    /* Dto -> Entity */
    public Post toEntity() {
        Post posts = Post.builder()
                .post_id(post_id)
                .user_id(user_id)
                .created_at(created_at)
                .thumbnail(thumbnail)
                .title(title)
                .content(content)
                .modified_at(modified_at)
                .build();

        return posts;
    }
}
