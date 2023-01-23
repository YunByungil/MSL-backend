package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.AllArgsConstructor;

import java.math.BigInteger;
@AllArgsConstructor
public class PostRequestDto {
    private Long id;

    private String thumbnail;
    private String title;
    private String content;

    public Post toEntity(){

        return new Post(id, thumbnail, title, content);
    }

}
