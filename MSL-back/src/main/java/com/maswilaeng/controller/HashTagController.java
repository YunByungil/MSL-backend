package com.maswilaeng.controller;

import com.maswilaeng.dto.common.ResponseDto;
import com.maswilaeng.dto.post.response.PostResponseDto;
import com.maswilaeng.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HashTagController {

    private final HashTagService hashTagService;

    @GetMapping("/tag")
    public ResponseEntity<?> getPostListHashTag(@RequestParam String tag) {
        List<PostResponseDto> postByHashTag = hashTagService.findPostByHashTag(tag);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postByHashTag));
    }
}
