package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.LoginResDTO;
import Maswillaeng.MSLback.dto.user.reponse.TokenResDTO;
import Maswillaeng.MSLback.dto.user.reponse.UserResDTO;
import Maswillaeng.MSLback.dto.user.request.UserJoinReqDTO;
import Maswillaeng.MSLback.dto.user.request.UserLoginReqDTO;
import Maswillaeng.MSLback.dto.user.request.UserUpdateReqDTO;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void join(UserJoinReqDTO userJoinReqDTO) throws Exception {
        User user = userJoinReqDTO.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateReqDTO userUpdateReqDTO) {
        User user = userRepository.findById(userId).get();
        user.update(userUpdateReqDTO);
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNickNameDuplicate(String nickName) {
        return userRepository.existsByEmail(nickName);
    }

    @Transactional(readOnly = true)
    public UserResDTO getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserResDTO(user);
    }

    public void userWithDraw(Long userId) {
        User user = userRepository.findById(userId).get();
        user.withdraw();
    }

    @Transactional
    public LoginResDTO login(UserLoginReqDTO userLoginReqDTO) throws Exception{
        User selectUser = userRepository.findByEmail(userLoginReqDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(userLoginReqDTO.getPassword(), selectUser.getPassword())) {
            throw new Exception("비밀번호를 다시 확인하세요.");
        } else if(selectUser.isWithdrawYn()) {
            throw new Exception("탈퇴한 회원입니다.");
        }
        String accessToken = jwtTokenProvider.createAccessToken(selectUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(selectUser);
        TokenResDTO tokenResDTO = TokenResDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        return LoginResDTO.builder().tokenResDTO(tokenResDTO).nickName(selectUser.getNickName()).userImage(selectUser.getUserImage()).build();
    }
}
