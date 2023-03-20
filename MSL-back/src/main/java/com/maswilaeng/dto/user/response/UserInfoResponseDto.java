package com.maswilaeng.dto.user.response;

import com.maswilaeng.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserInfoResponseDto {

    private String email;

    private String nickName;

    private String userImage;

    private String introduction;

    private boolean followState;

    private int followerCnt;

    private int followingCnt;

    public UserInfoResponseDto(User user, boolean isFollowed){
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.followState =  isFollowed;
        this.followerCnt = user.getFollowingList().size();
        this.followingCnt = user.getFollowerList().size();
    }

    public UserInfoResponseDto (User user) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.followState =  false;
        this.followerCnt = user.getFollowingList().size();
        this.followingCnt = user.getFollowerList().size();
    }
}
