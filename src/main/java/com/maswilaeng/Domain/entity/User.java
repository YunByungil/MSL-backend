package com.maswilaeng.Domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String pw;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, length = 100)
    private String phoneNumber;

    @Column
    private String userImage;

    @Column
    private String introduction;

    @Column(nullable = false, length = 100)
    private String withdraw_yn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private Role role;

    // 수정 필요
    @Column(nullable = false, length = 100)
    private String refresh_token;

    @Column(nullable = false, length = 100)
    @CreatedDate
    private LocalDateTime created_at;

    @Column
    @LastModifiedDate
    private LocalDateTime  modified_at;

    @Column
    private LocalDateTime withdraw_at;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post_id")
    private List<Post> post;

}
