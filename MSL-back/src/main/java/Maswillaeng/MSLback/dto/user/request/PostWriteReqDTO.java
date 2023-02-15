package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostWriteReqDTO {
    private String thumbnail;
    private String title;
    private String content;

    public Post toEntity(User user) {
        return Post.builder().user(user).thumbnail(thumbnail).title(title).content(content).build();
    }
}
