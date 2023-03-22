package com.maswilaeng.controller;

import com.maswilaeng.dto.post.request.PostLikeRequestDto;
import com.maswilaeng.service.PostLikeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/post-like")
    public ResponseEntity<?> insert(@RequestBody @Valid PostLikeRequestDto postLikeRequestDto) {
        postLikeService.insertLike(postLikeRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/post-like")
    public ResponseEntity<?> delete(@RequestBody @Valid PostLikeRequestDto postLikeRequestDto) {
        postLikeService.deleteLike(postLikeRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/post-hate")
    public ResponseEntity<?> insertHate(@RequestBody @Valid PostLikeRequestDto postLikeRequestDto) {
        postLikeService.insertHate(postLikeRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/post-hate")
    public ResponseEntity<?> deleteHate(@RequestBody @Valid PostLikeRequestDto postLikeRequestDto) {
        postLikeService.deleteHate(postLikeRequestDto);
        return ResponseEntity.ok().build();
    }
}
