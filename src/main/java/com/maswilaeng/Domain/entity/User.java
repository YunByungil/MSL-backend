package com.maswilaeng.Domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String pw;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, length = 100)
    private Long phoneNumber;

    @Column()
    private String userImage;

    @Column()
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
    private LocalDateTime created_at;

    @Column()
    private LocalDateTime modified_at;

    @Column()
    private LocalDateTime withdraw_at;
}
