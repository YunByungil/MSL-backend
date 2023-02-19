package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResponseDto {
        private String nickName;
        private String userImage;
}
