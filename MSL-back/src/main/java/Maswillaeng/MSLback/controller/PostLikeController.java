package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {

    private final PostLikeService postLikeService;

    /**
     * 게시글 좋아요
     * @param postId
     * @param authentication
     * @return
     */
    @PostMapping("/{postId}/like")
    public ResponseEntity likePost(@PathVariable("postId") Long postId, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        postLikeService.likePost(postId, userId);
        return ResponseEntity.ok().body("좋아요!");
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/{postId}/like")
    public ResponseEntity unlikePost(@PathVariable("postId") Long postId, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        postLikeService.unlikePost(postId, userId);
        return ResponseEntity.ok().body("좋아요 취소!");
    }
}
