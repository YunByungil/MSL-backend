package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    TokenResponseDto tokenResponse;
    private String nickName;
    private String userImage;
}
