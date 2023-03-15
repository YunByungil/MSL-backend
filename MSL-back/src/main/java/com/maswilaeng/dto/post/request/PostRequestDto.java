package com.maswilaeng.dto.post.request;

import com.maswilaeng.domain.entity.Category;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor // 인자 없이 객체 생성 가능
public class PostRequestDto {

    private String thumbnail;

    private String title;

    private String content;

    private String category;

    private Integer likeCount;

    private Integer hateCount;

    private Set<String> hashTagSet;

    /* Dto -> Entity */
    @Builder
    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .thumbnail(thumbnail)
                .title(title)
                .content(content)
                .category(Category.valueOf(category))
                .likeCount(0)
                .hateCount(0)
                .build();
    }
}
