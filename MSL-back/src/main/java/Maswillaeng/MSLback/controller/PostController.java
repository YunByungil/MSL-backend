package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.post.reponse.PostResponse;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateRequestDto;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public PostResponse addPost(Authentication authentication, @RequestBody PostRequestDto postRequestDto) {
        // TODO: @Valid
        Long id = Long.parseLong(authentication.getName());
        log.info("content = {}", postRequestDto.getContent());
        postService.addPost(id, postRequestDto);

        return new PostResponse(HttpStatus.OK.value());
    }

    @PutMapping("/post/{postId}")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto dto) {
        postService.updatePost(postId, dto);
        return new PostResponse(HttpStatus.OK.value());
    }
}
