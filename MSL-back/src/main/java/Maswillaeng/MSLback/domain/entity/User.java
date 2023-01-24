package Maswillaeng.MSLback.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Column(nullable = false, length = 30,unique = true)
    private String nickName;

    private String role;

    @Column(length = 1000)
    private String refreshToken; //RefreshToken

    private String userImage;

    @Column(length = 100)
    private String introduction;

    private int withdrawYn;

    private LocalDateTime withdrawAt;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken(){
        this.refreshToken = null;
    }

}
