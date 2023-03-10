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
public class RecommentResponseDto {

    private Long commentId;
    private String content;
    private String nickName;
    private Comment parent;
    private LocalDateTime createdAt;

//    private boolean isWritten;

    public static RecommentResponseDto of(Comment comment) {
        return RecommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .createdAt(comment.getCreatedAt())
                .parent(comment.getParent())
//                .isWritten(bool)
                .build();
    }
}
