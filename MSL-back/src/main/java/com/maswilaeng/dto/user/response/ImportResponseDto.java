package com.maswilaeng.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImportResponseDto {
    private String birth;
    private String unique_key; // CI key
}

