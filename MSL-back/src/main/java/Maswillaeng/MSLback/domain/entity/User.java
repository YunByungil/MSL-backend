package Maswillaeng.MSLback.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table( name = "users")
@Getter @Setter
public class User {


    @OneToMany
    @JoinColumn(name = "POST_ID")
    private List<Post> postList;

    @Id  @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //아이디 pk 자동생성
    private Long id;

    @NonNull
    private String email;

    @Column(name = "PW")
    private String pwd;

    private String nickName;

    private String phoneNumber;

    private String userImage;

    private String introduction;

    @Enumerated(EnumType.STRING)
    private withDraw withdraw_yn;
    public enum withDraw { Y , N }

    @Enumerated(EnumType.STRING)
    private userRole role;
    public enum userRole {
        //관리자와 일반 유저로 구분하고, 셀러 바이어는 행위로 분류 된다.
        BASIC, ADIN
    }

    private String refresh_token;

    @CreationTimestamp
    private Date creadted_at;

    @CreationTimestamp
    private Date modified_at;

    @CreationTimestamp
    private Date withdraw_at;


    @Builder
    public User(String email
                , String pwd
                , String phoneNumber
                , String nickName
                , String userImage
                , String introduction) {
        this.email = email;
        this.pwd = pwd;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.role = userRole.BASIC;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdraw_yn = withDraw.N;
    }

}
