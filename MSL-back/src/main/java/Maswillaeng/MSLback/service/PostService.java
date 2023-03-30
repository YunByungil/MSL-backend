package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.post.request.PostListRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    public Page<Post> getAllPosts(PostListRequestDto requestDto){
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());
        Page<Post> posts = postsRepository.findAll(pageable);
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

    public Page<Post> findPostsByUserId(Long userId, PostListRequestDto requestDto){
        userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다. id=" + userId));
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());
        Page<Post> posts = postsRepository.findAllByUserId(userId, pageable);
        return posts;
    }

    public Map<String,String> uploadImage(MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "MSL-back/src/main/resources/upload/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);

        return imagePath;
    }
}
