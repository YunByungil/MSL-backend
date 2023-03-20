package com.maswilaeng.dto.user.response;


import com.maswilaeng.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserFindResponseDto {

    private String email;
    private String nickName;
    private String phoneNumber;
    private String userImage;
    private String introduction;

    public UserFindResponseDto(User user) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.phoneNumber = user.getPhoneNumber();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
    }
}
