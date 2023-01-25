package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Posts;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long Id;
    private String thumbnail;
    private String title;
    private String content;

    public PostResponseDto(Posts entity) {
        this.Id = entity.getId();
        this.thumbnail = entity.getThumbnail();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}

