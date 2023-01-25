package com.maswilaeng.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UserForm {
    private Long user_id;
    private String email;
    private String pw;
    private String nickname;
    private Long phoneNumber;
    private String userImage;
    private String introduction;
}
