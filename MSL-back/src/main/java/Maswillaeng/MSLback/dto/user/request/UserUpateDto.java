package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
//회원정보 수정
public class UserUpateDto {
    private String pwd;
    private String nickname;
    private String phoneNumber;
    private String userImage;
    private String introduction;

}
