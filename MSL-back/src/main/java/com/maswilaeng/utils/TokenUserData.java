package com.maswilaeng.utils;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenUserData {
    private Long userId;
    private String userRole;

    public TokenUserData(Claims claims) {
        String userId = String.valueOf(claims.get("userId"));
        this.userId = Long.valueOf(userId);
        this.userRole = (String) claims.get("role");
    }
}
