package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.jwt.TokenRequest;
import Maswillaeng.MSLback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity createPost(@RequestBody PostSaveRequestDto post, @TokenRequest String userToken) {
        postService.save(post, userToken);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts")
    public ResponseEntity getPosts(@RequestParam(value = "page") int page) {
        return ResponseEntity.ok().body(postService.getPosts(page));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity getPost(@PathVariable int postId) throws Exception {
        return ResponseEntity.ok().body(postService.getPost((long) postId));
    }
}
