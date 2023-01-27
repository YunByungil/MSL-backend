package com.maswilaeng.service;

import com.maswilaeng.Domain.entity.User;
import com.maswilaeng.Domain.repository.UserRepository;
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