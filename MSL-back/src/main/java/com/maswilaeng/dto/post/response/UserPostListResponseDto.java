package com.maswilaeng.dto.post.response;

import com.maswilaeng.domain.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class UserPostListResponseDto{

    List<PostResponseDto> postList = new ArrayList<>();
    public UserPostListResponseDto(List<PostResponseDto> postList) {
        this.postList = postList;
    }
}
