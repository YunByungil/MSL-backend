package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//회원 가입
@NoArgsConstructor //파라메터가 없는 기본 생성자를 생성
@Getter
public class UserSignDto {

    private String email;

    private String pwd;

    private String nickname;

    private String phoneNumber;

    private String userImage;

    private String introduction;
}
