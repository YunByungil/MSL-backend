package Maswillaeng.MSLback.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    private String userImage;
    private String introduction;
    @ColumnDefault("'n'")
    private String withdrawYn;
    private String role;
    private String refresh_token;
    private LocalDateTime withdrawAt;

    @Builder
    public User(String email, String password, String nickname,
                String phoneNumber, String userImage, String introduction,
                String role, String refresh_token,
                LocalDateTime withdrawAt) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdrawYn = "n";
        this.role = role;
        this.refresh_token = refresh_token;
        this.withdrawAt = withdrawAt;
    }
}
