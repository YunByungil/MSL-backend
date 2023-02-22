package Maswillaeng.MSLback.dto.auth.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTokenResponseDto {
        private Long id;
        private String accessToken;
        private String refreshToken;

        public UserTokenResponseDto(Long id, String accessToken, String refreshToken) {
                this.id = id;
                this.accessToken = accessToken;
                this.refreshToken = refreshToken;
        }
}
