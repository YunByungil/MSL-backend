package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
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

    private final PostRepository postsRepository;
    private final UserRepository userRepository;
    public void savePost(Long userId, PostsSaveRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new IllegalStateException("회원이 존재하지 않습니다. id=" + userId));

        Post post = requestDto.toEntity(user);
        postsRepository.save(post);
    }

    public List<Post> getAllPosts(){
        List<Post> posts = postsRepository.findAll();
        return posts;
    }

    public Post getPostById(Long postId){
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("게시물이 존재하지 않습니다. id=" +  postId));
        return post;
    }

    public void updatePost(Long postId, PostsUpdateRequestDto requestDto) {

        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        ()-> new IllegalStateException("게시물이 존재하지 않습니다. id=" + postId)); //get null처리 안하고 강제
        post.update(requestDto);
    }

    public void deletePost(Long postId) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        () -> new IllegalArgumentException("게시물이 존재하지 않습니다. id=" + postId)
                );
        postsRepository.delete(post);
    }

    public List<Post> findPostsByUserId(Long userId){
        userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다. id=" + userId));
        List<Post> posts = postsRepository.findAllByUserId(userId);
        return posts;
    }
}
