package com.maswilaeng.dto.duplicate.request;

import com.maswilaeng.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DuplicateRequestDto {

    private String email;
    private String nickName;
    private String phoneNumber;

    @Builder
    public User toUser() {
        return User.builder()
                .email(email)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
