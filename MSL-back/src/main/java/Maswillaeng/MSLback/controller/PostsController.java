package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.response.PostResponseDto;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostService postService;

    @PostMapping("/post")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id){
        postService.delete(id);

        return id;
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }
}

