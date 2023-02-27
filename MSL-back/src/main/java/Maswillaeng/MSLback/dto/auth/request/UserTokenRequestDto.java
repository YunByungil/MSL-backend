package Maswillaeng.MSLback.dto.auth.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTokenRequestDto {
    private Long id;
    private String accessToken;
    private String refreshToken;

}
