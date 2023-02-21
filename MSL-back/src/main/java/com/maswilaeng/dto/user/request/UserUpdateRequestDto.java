package com.maswilaeng.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {

    private String password;
    private String phoneNumber;

    private String nickName;
    private String userImage;
    private String introduction;
}
