package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController

public class PostController {
    @Autowired
    private PostService postService;

    //전체조회
    @GetMapping("/post/page")
    public List<Post> index() {
        return postService.index();


    }
    //상세조회
    @GetMapping("/post/{id}")
    public Post show(@PathVariable Long id) {
        return postService.show(id);


    }

    //등록
    @PostMapping("/post")
    public ResponseEntity<Post> save(@RequestBody PostRequestDto dto) {
        Post create = postService.save(dto);


        return (create != null) ?

                 new ResponseEntity<>(create, HttpStatus.OK) :
                 new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    //수정
    @PatchMapping("/post/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id,
                                          @RequestBody PostRequestDto dto) {
        Post update = postService.update(id, dto);
        return (update != null) ?
                new ResponseEntity<>(update, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    //삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Post> delete(@PathVariable Long id) {

        Post delete = postService.delete(id);

        return (delete != null) ?
                new ResponseEntity<>(delete, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


    }


}
