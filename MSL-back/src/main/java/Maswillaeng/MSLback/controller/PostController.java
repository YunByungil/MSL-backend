package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    //받아올 포스트
    @PostMapping("/create") //api 주소에 추가로 태그 더붙이나?
    public String createPost(PostRequestDto postRequestDto){

    return "okay";
    }
}