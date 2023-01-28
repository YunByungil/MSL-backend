package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String password;
    private String phoneNumber;
    private String nickName;
    private String userImage;
    private String introduction;

}
