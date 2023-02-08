package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String userImage;
    private String introduction;
    private RoleType role;

    public User toEntity(){
        return User.builder().email(email).password(password).nickname(nickname)
                .phoneNumber(phoneNumber).userImage(userImage).introduction(introduction)
                .role(RoleType.USER).build();
    }

}
