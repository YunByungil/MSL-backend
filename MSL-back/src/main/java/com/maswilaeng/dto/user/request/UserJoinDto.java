package com.maswilaeng.dto.user.request;

import com.maswilaeng.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserJoinDto {

    private String email;

    private String password;

    private String nickName;

    private String userImage;

    public UserJoinDto(String email, String password, String nickName, String userImage) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.userImage = userImage;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .userImage(userImage)
                .build();
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .userImage(userImage)
                .build();
    }
}
