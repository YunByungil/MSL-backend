package com.maswilaeng.dto.post.response;

import com.maswilaeng.domain.entity.HashTag;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.Tag;
import com.maswilaeng.dto.comment.response.CommentResponseDto;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@ToString
public class PostResponseDto {
    private Long postId;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer likeCount;
    private Integer hateCount;
    private List<CommentResponseDto> commentList;
    private Set<String> hashTagSet;


    /* Dto -> Entity */
    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.likeCount = post.getLikeCount();
        this.hateCount = post.getHateCount();
        this.commentList = post.getCommentList().stream()
                .map(CommentResponseDto::new)   //여기서 문제 발생일듯
                .collect(Collectors.toList());
        this.hashTagSet = post.getHashTagSet().stream()
                .map(HashTag -> HashTag.getTag().getTagName())
                .collect(Collectors.toSet());

    }
}
