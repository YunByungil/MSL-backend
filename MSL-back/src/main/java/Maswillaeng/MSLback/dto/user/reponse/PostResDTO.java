package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;

@Getter
public class PostResDTO {
    private User user;
    // private Category category;
    private String thumbnail;
    private String title;
    private String content;
    private int hits;
    private int report;

    public PostResDTO(Post post) {
        this.user = post.getUser();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.hits = post.getHits();
        this.report = post.getReport();
    }
}
