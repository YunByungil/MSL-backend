package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private String email;
    private String phoneNumber;
    private String nickName;
    private String userImage;
    private String introduction;
    private String password;


    public UserResponseDto(String email, String phoneNumber, String nickName, String userImage, String introduction, String password) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.userImage = userImage;
        this.introduction = introduction;
        this.password = password;
    }
}
