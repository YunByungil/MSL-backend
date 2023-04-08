package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.dto.comment.request.CommentReplyRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentSaveRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import Maswillaeng.MSLback.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity saveComment(@AuthenticationPrincipal Long userId, @RequestBody CommentSaveRequestDto requestDto) {
        commentService.saveComment(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply")
    public ResponseEntity saveReply(@AuthenticationPrincipal Long userId, @RequestBody CommentReplyRequestDto requestDto) {
        commentService.saveReply(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateComment(@AuthenticationPrincipal Long userId, @RequestBody CommentUpdateRequestDto requestDto) throws AccessDeniedException {
        commentService.updateComment(userId, requestDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@AuthenticationPrincipal Long userId, @PathVariable("commentId") Long commentId) throws AccessDeniedException {
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reply/{parentId}")
    public ResponseEntity<?> findAllReplies(@PathVariable Long parentId) {
        List<CommentResponseDto> findAllReply = commentService.findAllReplies(parentId);
        return ResponseEntity.ok().body(findAllReply);
    }
}
