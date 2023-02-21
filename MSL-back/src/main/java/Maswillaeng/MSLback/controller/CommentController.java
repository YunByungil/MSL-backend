package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateDto;
import Maswillaeng.MSLback.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity addComment(@PathVariable("postId") Long postId,
                                     @RequestBody CommentRequestDto dto,
                                     Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        commentService.addComment(postId, userId, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/post/{postId}/{commentId}/comment")
    public ResponseEntity updateComment(@PathVariable("postId") Long postId,
                                        @PathVariable("commentId") Long commentId,
                                        @RequestBody CommentUpdateDto dto,
                                        Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        commentService.updateComment(postId, userId, commentId, dto);
        return ResponseEntity.ok().body(dto);
    }
}
