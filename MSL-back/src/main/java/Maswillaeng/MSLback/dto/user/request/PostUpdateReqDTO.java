package Maswillaeng.MSLback.dto.user.request;

import lombok.Getter;

@Getter
public class PostUpdateReqDTO {
    private long postId;
    private String thumbnail;
    private String title;
    private String content;
}
