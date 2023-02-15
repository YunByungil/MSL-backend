package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String thumbnail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ColumnDefault("0")
    private Long hits;

    @ColumnDefault("0")
    private int report;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<HashTag> hashTagList = new ArrayList<>();

    @Builder
    public Post(String thumbnail, String title, String content, User user, String category) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.user = user;
        this.hits = 1L;
        this.category = Category.valueOf(category);
    }

    public void update(PostUpdateDto postUpdateDto) {
        this.thumbnail = postUpdateDto.getThumbnail();
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        this.category = postUpdateDto.getCategory();
    }

}
