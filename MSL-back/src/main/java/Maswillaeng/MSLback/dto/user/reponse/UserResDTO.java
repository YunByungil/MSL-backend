package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;

@Getter
public class UserResDTO {
    private String email;
    private String password;
    private String nickName;
    private String phoneNumber;
    private String userImage;
    private String introduction;

    public UserResDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.phoneNumber = user.getPhoneNumber();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
    }
}
