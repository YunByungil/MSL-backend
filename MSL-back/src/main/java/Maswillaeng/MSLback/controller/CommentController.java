package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController("/api/comment")
public class CommentController {
    private final CommentService commentService;

}
