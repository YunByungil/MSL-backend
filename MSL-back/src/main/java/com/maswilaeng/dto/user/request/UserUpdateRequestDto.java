package com.maswilaeng.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {

    private String nickName;
    private String userImage;
    private String introduction;
}
