package Maswillaeng.MSLback.dto.post.reponse;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postId;
    private String nickName;
    private String title;
    private String thumbNail;
    private String content;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Long postId,String nickName, String title, String thumbNail, String content,LocalDateTime modifiedAt) {
        this.postId =postId;
        this.nickName = nickName;
        this.title = title;
        this.thumbNail = thumbNail;
        this.content = content;
        this.modifiedAt = modifiedAt;

    }

    public PostResponseDto(Long postId,String nickName, String title, String thumbNail,LocalDateTime modifiedAt) {
        this.postId =postId;
        this.nickName = nickName;
        this.title = title;
        this.thumbNail = thumbNail;
        this.modifiedAt = modifiedAt;
    }
}
