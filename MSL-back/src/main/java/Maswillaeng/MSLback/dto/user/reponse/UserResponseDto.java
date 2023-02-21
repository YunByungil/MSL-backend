package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private String email;
    private String phoneNumber;
    private String nickname;
    private String userImage;
    private String introduction;
    private String password;

    public UserResponseDto(User user){
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.nickname = user.getNickname();
//        this.userImage = user.getUserImage();
//        this.introduction = user.getIntroduction();
        this.password = user.getPassword();
    }
}
