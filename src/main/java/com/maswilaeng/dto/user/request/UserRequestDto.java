package com.maswilaeng.dto.user.request;

import com.maswilaeng.domain.entity.Role;
import com.maswilaeng.domain.entity.User;

import java.time.LocalDateTime;

public class UserRequestDto {
    private Long user_id;
    private String email;
    private String pw;
    private String nickName;
    private String phoneNumber;
    private String userImage;
    private String introduction;
    private String withdrawYn;
    private Role role;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime withdrawAt;

    /* DTO -> Entity */
    public User toEntity() {
        User user = User.builder()
                .id(user_id)
                .email(email)
                .pw(pw)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .userImage(userImage)
                .introduction(introduction)
                .withdrawYn(withdrawYn)
                .role(role.USER)
                .refreshToken(refreshToken)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .withdrawAt(withdrawAt)
                .build();
        return user;
    }
}
