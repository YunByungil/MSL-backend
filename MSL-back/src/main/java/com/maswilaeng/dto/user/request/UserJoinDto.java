package com.maswilaeng.dto.user.request;

import com.maswilaeng.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinDto {

    private String email;

    private String password;

    private String nickName;

    private String userImage;


    public User toUser() throws Exception {
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .userImage(userImage)
                .build();
    }

    public UserJoinDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
    }
}
