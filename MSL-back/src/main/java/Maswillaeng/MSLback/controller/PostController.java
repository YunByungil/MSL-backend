package Maswillaeng.MSLback.controller;


import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateRequestDto;

import Maswillaeng.MSLback.service.PostService;
import Maswillaeng.MSLback.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;




    @PostMapping("/post")
    public ResponseEntity createPost(@RequestBody PostSaveRequestDto post) {
        postService.save(post, UserContext.userId.get());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts")
    public ResponseEntity getPosts(@PageableDefault(sort = "createdAt" ,direction =  Sort.Direction.DESC,size = 20) Pageable pageable) {

        return  ResponseEntity.ok().body(postService.getPosts(pageable));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity getPost(@PathVariable int postId) throws Exception {
        return ResponseEntity.ok().body(postService.getPost((long) postId));
     }

    @PutMapping("post/{postId}")
    public ResponseEntity updatePost(@PathVariable int postId,@RequestBody PostUpdateRequestDto requestDto) throws Exception {
        postService.updatedPost((long) postId,UserContext.userId.get() ,requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity deletePost(@PathVariable int postId) throws Exception {
        postService.deletePost((long) postId,UserContext.userId.get());
        return ResponseEntity.ok().build();
    }

    //TODO : testCode



}
