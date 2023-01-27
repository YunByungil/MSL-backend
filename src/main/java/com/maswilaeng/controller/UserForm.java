package com.maswilaeng.controller;

import com.maswilaeng.Domain.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Getter
@Setter
public class UserForm {
    private Long user_id;
    private String email;
    private String pw;
    private String nickName;
    private Long phoneNumber;
    private String userImage;
    private String introduction;
    private String withdraw_yn;
    private Role role;
    private String refresh_token;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime modified_at;
    private LocalDateTime withdraw_at;
}
