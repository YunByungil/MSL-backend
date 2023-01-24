package Maswillaeng.MSLback.domain.repository.post;

import Maswillaeng.MSLback.domain.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> postList(int page);

   Post getPost(Long postId);
}
