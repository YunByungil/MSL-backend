package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Posts;

import java.time.LocalDateTime;

public class PostsListResponseDto {
    private Long id;
    private String title;
    private String thumbnail;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.thumbnail = entity.getThumbnail();
        this.modifiedDate = entity.getModifiedDate();
    }
}
