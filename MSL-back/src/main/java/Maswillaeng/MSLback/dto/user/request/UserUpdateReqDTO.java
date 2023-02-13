package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.Role;
import lombok.Getter;

@Getter
public class UserUpdateReqDTO {
    private String password;
    private String nickName;
    private String phoneNumber;
    private String userImage;
    private String introduction;
}
