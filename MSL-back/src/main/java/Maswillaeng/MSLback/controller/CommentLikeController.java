package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    /**
     * 댓글 좋아요
     */
    @PostMapping("/{commentId}/commentlike")
    public ResponseEntity likeComment(@PathVariable("commentId") Long commentId, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        commentLikeService.likeComment(commentId, userId);
        return ResponseEntity.ok().body("댓글 좋아요!");
    }

    @DeleteMapping("/{commentId}/commentlike")
    public ResponseEntity unlikeComment(@PathVariable("commentId") Long commentId, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        commentLikeService.unlikeComment(commentId, userId);
        return ResponseEntity.ok().body("댓글 좋아요 취소 완료!");
    }
}
