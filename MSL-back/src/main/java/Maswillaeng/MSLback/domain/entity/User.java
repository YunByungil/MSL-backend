package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.user.request.UserUpdateReqDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 30)
    private String nickName;

    private String userImage;

    @Column(length = 100)
    private String introduction;

    @ColumnDefault("0")
    private int withdrawYn;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 1000)
    public String refreshToken;

    private LocalDateTime withdrawAt;

    @Builder
    public User(long userId, String email, String password, String phoneNumber, String nickName, String userImage, String introduction, int withdrawYn, Role role, String refreshToken, LocalDateTime withdrawAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdrawYn = withdrawYn;
        this.role = role;
        this.refreshToken = refreshToken;
        this.withdrawAt = withdrawAt;
    }

    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        this.password = userUpdateReqDTO.getPassword();
        this.nickName = userUpdateReqDTO.getNickName();
        this.phoneNumber = userUpdateReqDTO.getPhoneNumber();
        this.userImage = userUpdateReqDTO.getUserImage();
        this.introduction = userUpdateReqDTO.getIntroduction();
    }

    public void withdraw() {
        this.withdrawYn = 1;
        this.withdrawAt = LocalDateTime.now();
    }
}
