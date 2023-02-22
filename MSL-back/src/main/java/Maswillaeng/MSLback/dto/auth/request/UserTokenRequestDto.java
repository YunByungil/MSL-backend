package Maswillaeng.MSLback.dto.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserTokenRequestDto {

    @Getter
    @NoArgsConstructor
    public class UserTokenResponseDto {
        private Long id;
        private String accessToken;

        public UserTokenResponseDto(Long id, String accessToken, String refreshToken) {
            this.id = id;
            this.accessToken = accessToken;
        }
    }

}
