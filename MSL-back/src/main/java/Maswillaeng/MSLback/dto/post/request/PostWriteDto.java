package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
public class PostWriteDto {
    private String thumbnail;
    private String title;
    private String content;
}
