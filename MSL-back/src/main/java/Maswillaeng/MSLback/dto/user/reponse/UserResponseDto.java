package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

//회원 정보 수정시 조회
@NoArgsConstructor
@Component
@Getter @Setter
public class UserResponseDto {

    private String email;

    private String nickname;

    private String phoneNumber;

    private String userImage;

    private String introduction;

}
