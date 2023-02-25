package Maswillaeng.MSLback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {

    @PostMapping("/{postId}/like")
    public ResponseEntity likePost(@PathVariable("postId") Long postId, Authentication authentication) {
        
        return ResponseEntity.ok().body("좋아요!");
    }
}
