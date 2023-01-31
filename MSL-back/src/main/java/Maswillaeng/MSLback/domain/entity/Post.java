package Maswillaeng.MSLback.domain.entity;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @JsonManagedReference
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private String thumbNail;


    public void update(String title,String content,String thumbNail){
        this.title = title;
        this.content =content;
        this.thumbNail =thumbNail;

    }

    @Builder
    public Post(Long postId, User user, String title, String content,String thumbNail) {
        this.postId = postId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.thumbNail = thumbNail;
    }


}
