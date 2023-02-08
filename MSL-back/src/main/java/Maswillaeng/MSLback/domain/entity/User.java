package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Currency;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String phoneNumber;

    private String userImage;

    @Column(length = 100)
    private String introduction;

    @ColumnDefault("0")
    private int withdrawYn;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String refreshToken;

    private LocalDateTime withdrawAt;

    @Builder
    public User(String email, String password, String nickname, String phoneNumber, String userImage,
                String introduction, RoleType role, String refreshToken) {
        this.email = email;
        this.password = password;
        this.nickName = nickname;
        this.phoneNumber = phoneNumber;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdrawYn = 0;
        this.role = role;
        this.refreshToken = refreshToken;
    }

    public void update(UserUpdateRequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.nickName = requestDto.getNickName();
        this.userImage = requestDto.getUserImage();
        this.introduction = requestDto.getIntroduction();
    }
}
