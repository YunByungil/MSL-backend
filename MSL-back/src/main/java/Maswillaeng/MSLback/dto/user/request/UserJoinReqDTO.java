package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.Role;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class UserJoinReqDTO {
    private String email;
    private String password;
    private String nickName;
    private String phoneNumber;
    private String userImage;
    private String introduction;
    private Role role;

    public User toEntity() {
        return User.builder().email(email).password(password).nickName(nickName).phoneNumber(phoneNumber).userImage(userImage).introduction(introduction).role(Role.User).build();
    }
}
