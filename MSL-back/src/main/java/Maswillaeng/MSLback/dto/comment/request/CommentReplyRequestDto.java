package Maswillaeng.MSLback.dto.comment.request;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReplyRequestDto {
    private User user;
    private Post post;
    private String content;
    private Comment comment;

    public Comment toEntity(Post post, User user, Comment comment) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .parent(comment)
                .build();
    }
}
