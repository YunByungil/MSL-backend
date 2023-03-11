package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.post.reponse.PostLikeResponseDto;
import Maswillaeng.MSLback.service.PostLikeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public PostLikeResponse likePost(@PathVariable("postId") Long postId, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        long likeCount = postLikeService.likePost(postId, userId);
        PostLikeResponseDto dto = new PostLikeResponseDto(userId, 1L, 1L, likeCount);
        return new PostLikeResponse(HttpStatus.OK.value(), "좋아요!", dto);
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

    @Getter
    @AllArgsConstructor
    static class PostLikeResponse<T> {

        private int code;
        private String message;
        private T result;
    }
}
