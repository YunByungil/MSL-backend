package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;

@Getter
public class PostSaveRequestDto {
    private String thumbNail;
    private String title;
    private String content;

    public PostSaveRequestDto(String thumbNail, String title, String content) {
        this.thumbNail = thumbNail;
        this.title = title;
        this.content = content;
    }

    public Post toEntity(User user){
        return Post.builder().title(title).content(content).thumbNail(thumbNail).user(user).build();
    }

}
