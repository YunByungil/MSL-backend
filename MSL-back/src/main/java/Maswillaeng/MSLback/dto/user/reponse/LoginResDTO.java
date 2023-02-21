package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDTO {
    TokenResDTO tokenResDTO;
    private String nickName;
    private String userImage;
}
