package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateRequestDto;
import Maswillaeng.MSLback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity createPost(@RequestBody PostSaveRequestDto post, @CookieValue("ACCESS_TOKEN") String userToken) {
        postService.save(post, userToken);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts")
    public ResponseEntity getPosts(@PageableDefault(sort = "createdAt" ,direction =  Sort.Direction.DESC) Pageable pageable) {

        return  ResponseEntity.ok().body(postService.getPosts(pageable));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity getPost(@PathVariable int postId) throws Exception {
        return ResponseEntity.ok().body(postService.getPost((long) postId));
    }

    @PutMapping("post/{postId}")
    public ResponseEntity updatePost(@PathVariable int postId,@RequestBody PostUpdateRequestDto requestDto,
                                     @CookieValue("ACCESS_TOKEN") String userToken) throws Exception {
        postService.updatedPost((long) postId, userToken,requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("post")
    public ResponseEntity deletePost(@RequestParam(value = "id") int postId,@CookieValue("ACCESS_TOKEN") String userToken) throws Exception {
        postService.deletePost((long) postId,userToken);
        return ResponseEntity.ok().build();
    }


}
