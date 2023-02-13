package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.repository.PostsRepository;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.response.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostsRepository postsRepository;

    public List<Post> getAllPosts(){
        List<Post> posts = postsRepository.findAll();
        return posts;
    }

    public Optional<Post> getPostById(Long postId){
        Optional<Post> post = postsRepository.findById(postId);
        return post;
    }

    public void savePost(PostsSaveRequestDto requestDto) {
        Post post = requestDto.toEntity();
        postsRepository.save(post);
    }

    public void updatePost(Long id, PostsUpdateRequestDto requestDto) {
        Post posts = postsRepository.findById(id).get();
        posts.update(requestDto);
    }

    public void deletePost(Long id) {
        Post post = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
                );
        postsRepository.delete(post);
    }
}
