package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();
//    private Long viewCount;     //조회수
//    private String delYn;       //삭제여부

    @Builder
    public Post(String title, String content, String thumbnail, User user) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.user = user;
    }

    public void update(PostsUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.thumbnail= requestDto.getThumbnail();
    }
}
