package com.maswilaeng.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
}