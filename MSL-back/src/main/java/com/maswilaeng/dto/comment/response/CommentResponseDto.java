package com.maswilaeng.dto.comment.response;

import com.maswilaeng.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;

    private String content;

    private String nickName;

    private Long parentId;

    private LocalDateTime createdAt;

    private Integer likeCount;

    private Integer hateCount;

    public CommentResponseDto(Comment comment) {
        Long haveParent = null;
        if (comment.getParent() != null) {
            haveParent = comment.getParent().getId();
        }

        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.nickName = comment.getUser().getNickName();
        this.parentId = haveParent;
        this.createdAt = comment.getCreatedAt();
        this.hateCount = comment.getHateCount();
        this.likeCount = comment.getLikeCount();
    }

    public static CommentResponseDto of(Comment comment) {

        Long haveParent = null;
        if (comment.getParent() != null) {
            haveParent = comment.getParent().getId();
        }

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .createdAt(comment.getCreatedAt())
                .parentId(haveParent)
                .likeCount(comment.getLikeCount())
                .hateCount(comment.getHateCount())
                .build();
    }
}
