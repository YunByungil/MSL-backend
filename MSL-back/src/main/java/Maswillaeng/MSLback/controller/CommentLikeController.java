package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
