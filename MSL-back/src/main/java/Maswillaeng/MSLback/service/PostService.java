package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {
    @Autowired
    private    PostRepository postRepository;

    //전체조회
    public List<Post> index() {

        return postRepository.findAll();
    }


    //상세 조회
    public Post show(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    //등록
    public Post save(PostRequestDto dto) {

        Post post = dto.toEntity();
        if (post.getId() != null) {

            return null;
        }
        return postRepository.save(post);
    }

    //수정
    public Post update(Long id, PostRequestDto dto) {

        Post post = dto.toEntity();


        Post target = postRepository.findById(id).orElse(null);


        if (target == null || !id.equals(post.getId())) {


            return null;
        }

        target.patch(post);
        Post update = postRepository.save(target);
        return update;
    }

    //삭제
    public Post delete(Long id) {

        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {

            return null;
        }

        postRepository.delete(post);

        return post;
    }


}
