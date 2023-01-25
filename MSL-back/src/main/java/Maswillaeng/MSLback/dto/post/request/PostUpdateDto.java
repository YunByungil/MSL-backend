package Maswillaeng.MSLback.dto.post.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class PostUpdateDto {
    @Id @GeneratedValue
    @Column(name = "test")
    private Long post_id;
    private Long user_id;
    private Long thumbnail;
    private String title;
    private String content;
}
