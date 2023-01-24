package Maswillaeng.MSLback.dto.post.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostListResponseDto {
    private int totalCount;
    private List<PostResponseDto> post;
}
