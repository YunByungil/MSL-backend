package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.user.request.PostUpdateReqDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // private Category category;

    private String thumbnail;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ColumnDefault("0")
    private int hits;

    @ColumnDefault("0")
    private int report;

    @Builder
    public Post(long postId, User user, String thumbnail, String title, String content, int hits, int report) {
        this.postId = postId;
        this.user = user;
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.report = report;
    }

    public void update(PostUpdateReqDTO postUpdateReqDTO) {
        this.thumbnail = postUpdateReqDTO.getThumbnail();
        this.title = postUpdateReqDTO.getTitle();
        this.content = postUpdateReqDTO.getContent();
    }

}
