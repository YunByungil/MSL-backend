package Maswillaeng.MSLback.dto.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static Maswillaeng.MSLback.Util.AuthConstants.ACCESS_EXPIRE;

@Getter
@NoArgsConstructor
public class TokenResponseDto {
        private Long userId;
        private String accessToken;
        private String tokenType;
        private Integer expires_in;
        private String refreshToken;
        @Builder
        public TokenResponseDto(Long userId, String accessToken, String tokenType, Integer expires_in, String refreshToken) {
                this.userId = userId;
                this.accessToken = accessToken;
                this.tokenType = tokenType;
                this.expires_in = expires_in;
                this.refreshToken = refreshToken;
        }
}
