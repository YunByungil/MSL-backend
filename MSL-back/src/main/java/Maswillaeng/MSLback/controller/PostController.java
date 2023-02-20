package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.user.request.PostUpdateReqDTO;
import Maswillaeng.MSLback.dto.user.request.PostWriteReqDTO;
import Maswillaeng.MSLback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    // 글 작성
    @PostMapping("/post")
    public ResponseEntity<?> writePost(@RequestParam Long userId, @RequestBody PostWriteReqDTO postWriteReqDTO) {
        postService.save(userId, postWriteReqDTO);
        return ResponseEntity.ok().build();
    }

    // 글 수정
    @PutMapping("/post")
    public ResponseEntity<?> updatePost(Long postId, @RequestBody PostUpdateReqDTO postUpdateReqDTO) {
        postService.updatePost(postId, postUpdateReqDTO);
        return ResponseEntity.ok().build();
    }

    // 1개 글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<Object> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPost(postId));
    }

    // 글 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

}
