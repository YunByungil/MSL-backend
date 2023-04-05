package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Category;
import Maswillaeng.MSLback.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String thumbnail;
    private Category category;
    private LocalDateTime createdDate;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.nickname = entity.getUser().getNickname();
        this.title = entity.getTitle();
        this.thumbnail = entity.getThumbnail();
        this.category = entity.getCategory();
        this.createdDate = entity.getCreatedDate();
    }
}
