package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class PostUpdateRequestDto {
    private String thumbNail;
    private String title;
    private String content;


}
