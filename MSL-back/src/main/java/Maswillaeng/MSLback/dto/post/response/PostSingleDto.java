package Maswillaeng.MSLback.dto.post.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@Getter @Setter
public class PostSingleDto {

    private Long post_id;
    private String thumbnail;
    private String title;
    private String content;

    private String nickname;
    private String userimage;
}
