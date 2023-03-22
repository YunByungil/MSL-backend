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
public class ReCommentResponseDto {

    private Long commentId;
    private String content;
    private String nickName;
    private Long parentId;
    private LocalDateTime createdAt;

//    private boolean isWritten;

    public static ReCommentResponseDto of(Comment comment) {
        return ReCommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .createdAt(comment.getCreatedAt())
                .parentId(comment.getParent().getId())
//                .isWritten(bool)
                .build();
    }
}
