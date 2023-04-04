package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Category;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String thumbnail;
    private Category category;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .category(category)
                .build();
    }
}
