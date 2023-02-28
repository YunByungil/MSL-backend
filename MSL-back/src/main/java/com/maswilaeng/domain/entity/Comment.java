package com.maswilaeng.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

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
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;

    // 대댓글을 위한 부모 자식관계 설정
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_comment")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Comment(Post post, User user, String content, Comment parent) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.parentComment = parent;
    }

    public void updateComment(String content) {
        this.content = content;
    }
}

