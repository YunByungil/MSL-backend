package Maswillaeng.MSLback.dto.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
    private String email;
    private String password;
}
