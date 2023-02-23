package com.maswilaeng.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {

    private String nickName;
    private String userImage;
}
