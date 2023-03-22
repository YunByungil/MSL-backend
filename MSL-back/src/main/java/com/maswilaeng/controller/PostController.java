package com.maswilaeng.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.request.PostUpdateDto;
import com.maswilaeng.dto.post.response.PostListResponseDto;
import com.maswilaeng.dto.post.response.PostResponseDto;
import com.maswilaeng.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    /* CREATE */
    @PostMapping("/post")
    public ResponseEntity savePost(@RequestBody PostRequestDto dto) {
        postService.savePost(dto);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK));
    }

    /* READ */
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostDetails(@PathVariable Long postId) {
        PostResponseDto dto = postService.findPostById(postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, dto
        ));
    }

    /* UPDATE */
    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostUpdateDto postUpdateDto) throws Exception {
        postService.updatePost(postUpdateDto);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK));
    }

    /* DELETE */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) throws Exception {
        postService.deletePost(postId);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK));
    }

    /* List of posts*/
    @GetMapping("/post")
    public ResponseEntity<Object> getAllPost() throws JsonProcessingException {
        List<Post> posts = postService.searchAll();

        Set<PostListResponseDto> result = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toSet());
        return ResponseEntity.ok().body(result);
    }

    /* user Post */
    @GetMapping("/userPostList/{userId}")
    public ResponseEntity<Object> getUserPost(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(postService.findPostListByUserId(userId));
    }

}


