package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String thumbnail;
    private String title;
    private String content;
    private Category category;
    private Set<String> tag = new LinkedHashSet<>();

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .thumbnail(thumbnail)
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}
