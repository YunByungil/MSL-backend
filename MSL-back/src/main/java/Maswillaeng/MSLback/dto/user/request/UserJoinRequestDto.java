package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserJoinRequestDto {
    private String email;
    private String password;
    private String nickName;
    private String phoneNumber;
    private String userImage;
    private String introduction;
    private RoleType role;

    public User toEntity(){
      return User.builder().email(email).password(password).nickName(nickName).phoneNumber(phoneNumber).userImage(userImage).introduction(introduction).role(RoleType.USER).build();

    }

}
