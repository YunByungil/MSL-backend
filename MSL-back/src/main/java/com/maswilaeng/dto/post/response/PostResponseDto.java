package com.maswilaeng.dto.post.response;

import com.maswilaeng.domain.entity.HashTag;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.Tag;
import com.maswilaeng.dto.comment.response.CommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private Long userId;
    private String nickName;
    private String userImage;
    private String thumbnail;
    private String title;
    private String content;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer likeCount;
    private Integer hateCount;
    private List<CommentResponseDto> commentList;
    private Set<String> hashTagSet;
    private Long hits;
    private boolean isLiked;


    /* Dto -> Entity */
    public PostResponseDto(Post post) {
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickName();
        this.userImage = post.getUser().getUserImage();
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
        this.isLiked = false;
        this.hits = post.getHits();
        this.phoneNumber = post.getUser().getPhoneNumber();
    }

    public PostResponseDto(Post post, Long userId) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickName();
        this.phoneNumber = post.getUser().getPhoneNumber();
        this.userImage = post.getUser().getUserImage();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.hateCount = post.getHateCount();
        this.hashTagSet = post.getHashTagSet().stream()
                .map(HashTag -> HashTag.getTag().getTagName())
                .collect(Collectors.toSet());
        this.commentList = post.getCommentList().stream()
                .map(CommentResponseDto::new)   //여기서 문제 발생일듯
                .collect(Collectors.toList());
        this.hits = post.getHits();
        this.isLiked = post.getPostLikeSet().stream().anyMatch(postLike -> postLike.getUser().getId().equals(userId));
    }
}
