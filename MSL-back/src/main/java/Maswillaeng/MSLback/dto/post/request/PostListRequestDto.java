package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.enums.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostListRequestDto {

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

    public PostListRequestDto(Post post) {
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
    }
}
