package Maswillaeng.MSLback.dto.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
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
