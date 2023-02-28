package com.maswilaeng.controller;

import com.maswilaeng.dto.comment.request.CommentRequestDto;
import com.maswilaeng.dto.comment.request.CommentUpdateRequestDto;
import com.maswilaeng.dto.comment.request.RecommentRequestDto;
import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.service.CommentService;
import com.maswilaeng.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentRequestDto dto) {
        commentService.generateComment(UserContext.userData.get().getUserId(), dto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 등록 완료"
        ));
    }

    @PostMapping("/recomment")
    public ResponseEntity<?> saveReComment(@RequestBody RecommentRequestDto dto) {
        commentService.generateRecomment(UserContext.userData.get().getUserId(), dto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 등록 완료"
        ));
    }

    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequestDto dto) throws ValidationException {
        commentService.updateComment(dto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 수정 완료"
        ));
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws ValidationException {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 삭제 완료"
        ));
    }
}
