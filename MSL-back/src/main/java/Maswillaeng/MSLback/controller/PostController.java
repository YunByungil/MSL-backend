package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.request.PostListRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.response.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.response.PostResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity savePost(@AuthenticationPrincipal Long userId, @RequestBody PostsSaveRequestDto requestDto) {
        Long postId = postService.savePost(userId, requestDto);
        return ResponseEntity.ok().body(postId);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> getAllPosts(@RequestBody PostListRequestDto requestDto) {
        System.out.println(requestDto.getPage() + requestDto.getSize());
        Page<Post> posts = postService.getAllPosts(requestDto);
        List<PostListResponseDto> postList = posts.stream()
                .map(post -> new PostListResponseDto(post.getId(), post.getUser().getNickname(), post.getThumbnail(), post.getTitle()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(postList);
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> findPostsByUserId(@PathVariable Long userId, @RequestBody PostListRequestDto requestDto){
        Page<Post> posts = postService.findPostsByUserId(userId, requestDto);
        List<PostListResponseDto> postList = posts.stream()
                .map(post -> new PostListResponseDto(post.getId(), post.getUser().getNickname(), post.getThumbnail(), post.getTitle()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(postList);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> userImageUpdate(@RequestParam("photo") MultipartFile imageFile) throws IOException {

        return ResponseEntity.ok().body(postService.uploadImage(imageFile));
    }
}

