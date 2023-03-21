package com.maswilaeng.domain.entity;

import com.maswilaeng.dto.post.request.PostUpdateDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장
@ToString
// NoArgsConstructor : 객체 생성시 초기 인자 없이 객체를 생성
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ColumnDefault("0")
    private Long hits;

    @OneToMany(mappedBy = "post")
    private Set<PostLike> postLikeSet = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private Set<HashTag> hashTagSet;

    /* 게시글 수정 */
    public void update(PostUpdateDto postUpdateDto) {

        this.thumbnail = postUpdateDto.getThumbnail();
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
    }

    // Java Build design pattern. 생성 시점에 값 채우기
    @Builder
    public Post(String thumbnail, String title, String content, User user, Category category, Integer likeCount, Integer hateCount, Long hits) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.user = user;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.hits = hits;
    }

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @ColumnDefault("0")
    @Column(name = "hate_count", nullable = false)
    private Integer hateCount;

    public void setHashTagSet(Set<HashTag> hashTagSet) {
        this.hashTagSet = hashTagSet;
    }

    public void increaseHits() {
        this.hits = this.hits + 1L;
    }

}

