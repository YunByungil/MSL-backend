package com.maswilaeng.dto.user.response;

import lombok.*;

import java.util.HashMap;

@Getter
@NoArgsConstructor
public class LoginSuccessResponseDto {

    private String userImage;

    private String nickName;

    private String accessTokenExpiresIn;

//    private HashMap<String, Object> result = new HashMap<>();
//        result.put("nickName", user.getNickName());
//        result.put("userImage", user.getUserImage());
//        result.put("remainTime", remainingTime);

    @Builder
    public LoginSuccessResponseDto(String userImage, String nickName, String accessTokenExpiresIn) {
        this.nickName = nickName;
        this.userImage = userImage;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }
}
