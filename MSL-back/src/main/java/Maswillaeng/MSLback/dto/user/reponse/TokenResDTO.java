package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResDTO {
    private String accessToken;
    private String refreshToken;
}
