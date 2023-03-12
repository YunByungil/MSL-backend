package com.maswilaeng.controller;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.response.PostResponseDto;
import com.maswilaeng.dto.user.request.UserRequestDto;
import com.maswilaeng.dto.user.response.UserResponseDto;
import com.maswilaeng.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    /* CREATE */
    @PostMapping("/post")
    public ResponseEntity save(@RequestBody PostRequestDto dto, String nickname) {
//        return ResponseEntity.ok(postService.save(dto, nickname));
        return ResponseEntity.ok().body(postService.save(dto, nickname));
        //ResponseEntity search
        // test
    }

    /* READ */
    @GetMapping("/post/{postId}")
    public ResponseEntity read(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    /* UPDATE */
    // updateMapping 이었는지 put이었는지 기억이 안남.
    @PutMapping("/post/{postId}")
    public ResponseEntity update(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        postService.update(postId, dto);
        return ResponseEntity.ok(postId);
    }

    /* DELETE */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok(postId);
    }



    /* 글 작성 */
    @GetMapping("/posts/write")
    public String write(UserResponseDto user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-write";
    }

    /* 글 상세보기 */
    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable Long id, UserResponseDto user, Model model) {
        PostResponseDto dto = postService.findById(id);

        model.addAttribute("post", dto);
        return "post/{id}";
    }

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable Long id, UserRequestDto user, Model model) {
        PostResponseDto dto = postService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("post", dto);

        return "post/posts-update";
    }

    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable, UserResponseDto user) {
        Page<Post> searchList = postService.search(keyword, pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        return "posts/posts-search";
    }
}
