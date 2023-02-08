package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Post;

import java.time.LocalDateTime;

public class PostListResponseDto {
    private Long id;
    private String title;
    private String thumbnail;
    private LocalDateTime modifiedDate;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.thumbnail = entity.getThumbnail();
        this.modifiedDate = entity.getModifiedDate();
    }
}
