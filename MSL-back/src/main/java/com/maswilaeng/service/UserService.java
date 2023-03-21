package com.maswilaeng.service;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.dto.user.request.UserUpdateRequestDto;
import com.maswilaeng.dto.user.response.UserFindResponseDto;
import com.maswilaeng.dto.user.response.UserInfoResponseDto;
import com.maswilaeng.dto.user.response.UserResponseDto;
import com.maswilaeng.jwt.AESEncryption;
import com.maswilaeng.utils.SecurityUtil;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FollowService followService;
    private final AESEncryption aesEncryption;

    public User join(UserJoinDto userJoinDto) throws Exception {
        if (userRepository.findByEmail(userJoinDto.getEmail()).orElse(null) != null)
            throw new DuplicateRequestException("이미 가입 되어있는 유저입니다.");

        User user = User.builder()
                        .nickName(userJoinDto.getNickName())
                        .password(aesEncryption.encrypt(userJoinDto.getPassword()))
                        .email(userJoinDto.getEmail())
                        .userImage(userJoinDto.getUserImage())
                        .introduction("hi")
                        .build();

        return userRepository.save(user);

    }


    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public UserFindResponseDto findOne(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 회원입니다.")
        );
        UserFindResponseDto dto = new UserFindResponseDto(user);
        return dto;
    }

//    public UserInfoResponseDto



    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }

    public UserInfoResponseDto getUser(Long userId){
        User user = userRepository.findIfFollowingById(userId);

        // 로그인 하지 않은 사용자 체크
        Class<? extends Authentication> aClass = SecurityContextHolder.getContext().getAuthentication().getClass();
        if(aClass.equals(AnonymousAuthenticationToken.class)) {
            return new UserInfoResponseDto(user);
        } else {
            User loginUser = userRepository.findIfFollowedById(SecurityUtil.getCurrentUserId());
            boolean following = followService.isFollowing(userId, loginUser.getId());
            return new UserInfoResponseDto(user, following);
        }
    }

    public Optional<User> getUserEntity(Long userId) {
        return userRepository.findById(userId);
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
