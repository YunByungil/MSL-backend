package com.maswilaeng.dto.post.request;

import com.maswilaeng.domain.entity.Category;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 인자 없이 객체 생성 가능
public class PostRequestDto {

    private String thumbnail;

    private String title;

    private String content;

    private String category;

    /* Dto -> Entity */
    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .thumbnail(thumbnail)
                .title(title)
                .content(content)
                .category(Category.valueOf(category))
                .build();
    }
}
