package Maswillaeng.MSLback.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String password;
    private String phoneNumber;
    private String nickName;
    private String userImage;
    private String introduction;

}