package com.maswilaeng.service;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {

    @Autowired
    UserService userService;
    UserRepository userRepository;
}