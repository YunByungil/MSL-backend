package Maswillaeng.MSLback.dto.post.response;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String thumbnail;

}
