package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String email;
    private String phoneNumber;
    private String nickName;
    private String userImage;
    private String introduction;


    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
    }
}
