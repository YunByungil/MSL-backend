package com.maswilaeng.dto.user.response;

import com.maswilaeng.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    private String email;

    private String nickname;

    private String userImage;

    private String phoneNumber;

    private String introduction;

    public UserInfoResponseDto (User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickName();
        this.phoneNumber = user.getPhoneNumber();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
    }
}
