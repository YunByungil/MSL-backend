package com.maswilaeng.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.request.PostUpdateDto;
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
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        Post post = postService.findPostById(postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, new PostResponseDto(post)
        ));
    }

    /* UPDATE */
    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable @RequestBody PostUpdateDto postUpdateDto) throws Exception {
        postService.updatePost(postRepository.findUserIdById(postUpdateDto.getId()), postUpdateDto);
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
    public ResponseEntity<Map<String, Object>> getAllPost() throws JsonProcessingException {
        List<Post> posts = postService.searchAll();

        Map<String, Object> result = new HashMap<>();

        for (Post post : posts) {
            PostResponseDto dto = new PostResponseDto(post);
            result.put(String.valueOf(dto.getPostId()), dto);
        }

        result.put("code", HttpStatus.OK.value());
        result.put("totalCount", result.size() - 1);

        return ResponseEntity.ok().body(result);
    }
}

//fetch , dto로 빼는것

