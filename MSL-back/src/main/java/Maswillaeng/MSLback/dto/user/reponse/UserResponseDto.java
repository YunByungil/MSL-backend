package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponseDto {
    private String email;
    private String phoneNumber;
    private String nickName;
    private String userImage;
    private String introduction;
    private String password;


    public UserResponseDto toEntity(User user){
        return new UserResponseDto(
            user.getEmail(),
            user.getPhoneNumber(),
            user.getNickName(),
            user.getUserImage(),
            user.getIntroduction(),
            user.getPassword());
    }
}
