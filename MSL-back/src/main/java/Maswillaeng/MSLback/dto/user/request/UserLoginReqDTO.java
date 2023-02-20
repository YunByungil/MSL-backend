package Maswillaeng.MSLback.dto.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginReqDTO {
    private String email;
    private String password;
}
