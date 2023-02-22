package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Role;
import com.maswilaeng.domain.entity.RoleType;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.dto.user.request.UserUpdateRequestDto;
import com.maswilaeng.dto.user.response.UserInfoResponseDto;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(UserJoinDto userJoinDto){
        if (userRepository.findOneWithAuthoritiesByEmail(userJoinDto.getEmail()).orElse(null) != null)
            throw new DuplicateRequestException("이미 가입 되어있는 유저입니다.");

        User user = User.builder()
                        .nickName(userJoinDto.getNickName())
                        .password(passwordEncoder.encode(userJoinDto.getPassword()))
                        .email(userJoinDto.getEmail())
                        .userImage(userJoinDto.getUserImage())
                        .introduction("hi")
                        .build();

        return userRepository.save(user);

    }



    public List<User> findUsers(){

        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }


    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }




    public UserInfoResponseDto getUser(Long userId){
        User user = userRepository.findById(userId).get();
        return UserInfoResponseDto.of(user);
    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User selectedUser = userRepository.findById(userId).get();

        selectedUser.update(requestDto); //더티체킹
    }
    public void userWithdraw(Long userId) {
        User user = userRepository.findById(userId).get();
        user.withdraw();

    }
}
