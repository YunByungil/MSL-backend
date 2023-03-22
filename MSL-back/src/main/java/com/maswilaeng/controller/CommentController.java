package com.maswilaeng.controller;

import com.maswilaeng.dto.comment.request.CommentRequestDto;
import com.maswilaeng.dto.comment.request.CommentUpdateRequestDto;
import com.maswilaeng.dto.comment.request.ReCommentRequestDto;
import com.maswilaeng.dto.comment.response.CommentResponseDto;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.createComment(dto));
    }

    @PostMapping("/reComment")
    public ResponseEntity<?> saveReComment(@RequestBody ReCommentRequestDto dto) {

        return ResponseEntity.ok(commentService.createReComment(dto));
    }

    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequestDto dto) {
        commentService.updateComment(dto);

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK,
                "댓글 수정 완료"
        ));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK,
                "댓글 삭제 완료"
        ));
    }

    @GetMapping("/comment/list")
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam(name = "id") Long id) {

        List<CommentResponseDto> comment = commentService.getComment(id);

        return ResponseEntity.ok(comment);
    }
}
