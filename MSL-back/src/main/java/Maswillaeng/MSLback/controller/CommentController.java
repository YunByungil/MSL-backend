package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.comment.request.CommentReplyRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController("/api/{postId}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity saveComment(@PathVariable("postId") Long postId,
                                      @RequestBody CommentRequestDto requestDto) {
        commentService.saveComment(postId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}")
    public ResponseEntity saveReply(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestBody CommentReplyRequestDto requestDto) {
        commentService.saveReply(postId, commentId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable("postId") Long postId,
                                        @PathVariable("commentId") Long commentId,
                                        @RequestBody CommentUpdateRequestDto requestDto) {
        commentService.updateComment(postId, commentId, requestDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
