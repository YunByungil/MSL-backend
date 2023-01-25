package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String thumbnail;

    @Builder
    public PostSaveRequestDto(String title, String content, String thumbnail) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .build();
    }
}
