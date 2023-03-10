package com.maswilaeng.dto.user.response;

import lombok.*;

@Getter
@NoArgsConstructor
public class LoginSuccessResponseDto {

    private String userImage;

    private String nickName;

    private long accessTokenExpiresIn;

    @Builder
    public LoginSuccessResponseDto(String userImage, String nickName, long accessTokenExpiresIn) {
        this.nickName = nickName;
        this.userImage = userImage;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }
}
