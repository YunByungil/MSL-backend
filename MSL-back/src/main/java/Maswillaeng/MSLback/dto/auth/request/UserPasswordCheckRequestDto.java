package Maswillaeng.MSLback.dto.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordCheckRequestDto {
    Long userId;
    String password;
}
