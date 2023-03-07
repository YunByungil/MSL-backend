package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.enums.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {

    private Long postId;
    private Long userId;
    private String nickname;
    private String thumbnail;
    private String title;
    private String content;
    private Long hits;
    private Category category;
    private Long commentCount;
    private Long likeCount;
    private LocalDateTime createAt;

    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.thumbnail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.hits = post.getHits();
        this.category = post.getCategory();
        this.commentCount = post.getComment().stream().count();
        this.likeCount = post.getPostLike().stream().count();
        this.createAt = post.getCreateAt();
    }
}
