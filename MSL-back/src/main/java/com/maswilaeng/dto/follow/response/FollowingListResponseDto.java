package com.maswilaeng.dto.follow.response;

import com.maswilaeng.domain.entity.User;
import lombok.Getter;

@Getter
public class FollowingListResponseDto {

    private Long userId;

    private String nickName;

    private String userImage;

    public FollowingListResponseDto(User user) {
        this.userId = user.getId();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
    }

}
