package Maswillaeng.MSLback.domain.repository.post;


import Maswillaeng.MSLback.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom{

    //  @Query("select * from ")
//    Optional<Post> getPost(Long postId);
}
