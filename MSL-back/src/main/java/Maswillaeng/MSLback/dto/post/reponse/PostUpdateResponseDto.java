package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class PostUpdateResponseDto {
    private String thumbNail;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;

    public PostUpdateResponseDto(Post updatedPost) {
        this.title = updatedPost.getTitle();
        this.content  = updatedPost.getContent();
        this.thumbNail = updatedPost.getThumbNail();
        this.modifiedAt =updatedPost.getModifiedAt();
    }
}
