package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(length = 1000)
    private String refreshToken; //RefreshToken

    private String userImage;

    @Column(length = 100)
    private String introduction;

    private int withdrawYn;

    private LocalDateTime withdrawAt;

    @JsonBackReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();


    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken(){
        this.refreshToken = null;}

    @Builder
    public User(Long user_id, String email, String password, String phoneNumber, String nickName, RoleType role, String refreshToken, String userImage, String introduction, int withdrawYn, LocalDateTime withdrawAt, List<Post> posts) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.role = role;
        this.refreshToken = refreshToken;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdrawYn = withdrawYn;
        this.withdrawAt = withdrawAt;
        this.posts = posts;
    }

    public void update(UserUpdateRequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.nickName = requestDto.getNickName();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.userImage = requestDto.getUserImage();
        this.introduction = requestDto.getIntroduction();
    }

    public void withdraw() {
        this.withdrawYn = 1;
        this.withdrawAt = LocalDateTime.now();
    }
}

