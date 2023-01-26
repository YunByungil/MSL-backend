package Maswillaeng.MSLback.domain.repository.post;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Post> postList(Pageable pageable);

   Post getPost(Long postId);
}
