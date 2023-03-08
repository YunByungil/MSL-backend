package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.response.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.response.PostResponseDto;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;


    @PostMapping
    public ResponseEntity<Object> savePost(@RequestBody PostsSaveRequestDto requestDto) {
        postService.savePost(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<PostListResponseDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostListResponseDto> collect = posts.stream()
                .map(post -> new PostListResponseDto(post.getId(), post.getUser().getNickname(), post.getThumbnail(), post.getTitle()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable Long postId, @RequestBody PostsUpdateRequestDto requestDto) {
        postService.updatePost(postId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}

