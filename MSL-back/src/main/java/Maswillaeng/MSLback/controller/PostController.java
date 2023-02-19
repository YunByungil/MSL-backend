package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;


    @GetMapping("/page")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    @GetMapping
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Object> savePost(@RequestBody PostsSaveRequestDto requestDto) {
        postService.savePost(requestDto);
        return ResponseEntity.ok().build();
    }

/*    @PutMapping
    public ResponseEntity<Object> updatePost(@RequestBody PostsUpdateRequestDto requestDto, @PathVariable Long id) {
        postService.updatePost(id, requestDto);
        return ResponseEntity.ok().build();
    }*/

    @DeleteMapping
    public ResponseEntity<Object> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}

