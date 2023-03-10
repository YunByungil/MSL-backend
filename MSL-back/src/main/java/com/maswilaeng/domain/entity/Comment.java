package com.maswilaeng.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private String content;

    @Column(nullable = false)
    private boolean deleted;

    // 대댓글을 위한 부모 자식관계 설정
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Builder
    public Comment(Post post, User user, String content, Comment parent, Integer likeCount, Integer hateCount) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public Optional<Comment> findDeletableComment() {
        return hasChildren() ? Optional.empty() : Optional.of(findDeletableCommentByParent());
    }

    /** 자식의 개수가 1개일때만 삭제 가능하도록 */
    private Comment findDeletableCommentByParent() {
        if (isDeletableParent()) {
            Comment deletableParent = getParent().findDeletableCommentByParent();
            if (getParent().getChildren().size() == 1) return deletableParent;
        }
        return this;
    }

    /** 현재 댓글의 상위댓글이 제거해도 되는것인지 판별 */
    private boolean isDeletableParent() {
        return getParent()!= null && getParent().isDeleted() && getParent().getChildren().size()==1;
    }

    private boolean hasChildren() {
        return getChildren().size() != 0;
    }

    public void delete() {
        this.deleted = true;
    }

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @ColumnDefault("0")
    @Column(name = "hate_count", nullable = false)
    private Integer hateCount;
}
