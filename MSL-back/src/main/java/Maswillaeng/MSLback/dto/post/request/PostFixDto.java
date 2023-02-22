package Maswillaeng.MSLback.dto.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class PostFixDto {
    private Long post_id;
    private String thumbnail;
    private String title;
    private String content;
}
