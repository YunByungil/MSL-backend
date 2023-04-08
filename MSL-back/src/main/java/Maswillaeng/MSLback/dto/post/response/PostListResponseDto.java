package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Category;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private Long id;
    private String nickname;
    private String userImage;
    private String title;
    private String thumbnail;
    private Category category;
    private LocalDateTime createdDate;


    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.userImage = post.getUser().getUserImage();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.category = post.getCategory();
        this.createdDate = post.getCreatedDate();


    }
}
