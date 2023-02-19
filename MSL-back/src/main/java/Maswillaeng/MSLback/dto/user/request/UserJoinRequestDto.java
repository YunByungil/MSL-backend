package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.Role;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .build();
    }
}
