package Maswillaeng.MSLback.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String phoneNumber;
    private String userImage;
    private String introduction;
    @Column(nullable = false)
    private String withdrawYn;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String refresh_token;
    private LocalDateTime withdrawAt;


}
