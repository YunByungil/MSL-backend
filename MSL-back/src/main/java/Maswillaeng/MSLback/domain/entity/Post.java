package Maswillaeng.MSLback.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String thumbnail;
    @Column
    private String title;
    @Column
    private String content;

    public void patch(Post post) {
        if(post.id !=null)
            this.id = post.id;

        if(post.thumbnail !=null)
            this.thumbnail = post.thumbnail;
        if(post.title !=null)
            this.title = post.title;
        if(post.content !=null)
            this.content = post.content;
    }
}
