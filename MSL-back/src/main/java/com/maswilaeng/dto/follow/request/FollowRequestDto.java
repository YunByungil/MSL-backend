package com.maswilaeng.dto.follow.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRequestDto {

    private Long toUserId;

    private Long fromUserId;
}
