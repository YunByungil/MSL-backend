package Maswillaeng.MSLback.dto.post.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostRequestDto {
    /* Auto Increment 와 UUID 중 성능과 용량에 대한 issue로 Auto Increment 사용  */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long pId;
    private Long uId;
    private Long recipe_id;
    private Long creadted_at;
    private Long thumbnail;
    private String title;
    private String content;


}
