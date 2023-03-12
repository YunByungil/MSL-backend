package com.maswilaeng.dto.user.response;

import com.maswilaeng.domain.entity.Role;
import com.maswilaeng.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String user_id;
    private String email;
    private String pw;
    private String nickName;
    private Long phoneNumber;
    private String userImage;
    private String introduction;
    private String withdrawYn;
    private Role role;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime withdrawAt;

    /* DTO -> Entity */
    public UserResponseDto(User user) {
        //for git
        this.user_id = user_id;
        this.email = email;
        this.email = user.getEmail();
        this.pw = user.getPw();
        this.nickName = user.getNickName();
        this.phoneNumber = user.getPhoneNumber();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.withdrawYn = user.getWithdrawYn();
        this.role = user.getRole();
        this.refreshToken = user.getRefreshToken();






    }
}
