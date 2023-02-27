package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postsRepository;

    public List<Post> getAllPosts(){
        List<Post> posts = postsRepository.findAll(); //500개씩
        return posts;
    }

    public Optional<Post> getPostById(Long postId){
        Optional<Post> post = postsRepository.findById(postId);
        return post; //optional null처리  .orElse();
    }

    public void savePost(PostsSaveRequestDto requestDto) {
        Post post = requestDto.toEntity();
        postsRepository.save(post);
    }

    public void updatePost(Long postId, PostsUpdateRequestDto requestDto) {
        Post posts = postsRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("게시물이 존재하지 않습니다. id=" + postId)); //get null처리 안하고 강제
        posts.update(requestDto);
    }

    public void deletePost(Long postId) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        () -> new IllegalArgumentException("게시물이 존재하지 않습니다. id=" + postId)
                );
        postsRepository.delete(post);
    }
}
