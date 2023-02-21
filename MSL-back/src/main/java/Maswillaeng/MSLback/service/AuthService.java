package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void join(UserJoinRequestDto userJoinDto){
        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    public void login(UserLoginRequestDto requestDto) throws IllegalAccessException {
        User user = userRepository.findByEmail(requestDto.getEmail()).get();
        if(requestDto.getEmail() == null || requestDto.getPassword() == null){
            throw new IllegalAccessException("로그인 정보가 입력되지 않았습니다.");
        }
        String encodePw = passwordEncoder.encode(requestDto.getPassword());
        if(passwordEncoder.matches(user.getPassword(),encodePw)) {
            TokenDto tokenDto = tokenProvider.generateTokenDto(requestDto.toString());
            RefreshToken refreshToken = RefreshToken.builder().build();

            refreshTokenRepository.save(refreshToken);
        }else {
            throw new IllegalAccessException("")


        // 5. 토큰 발급
        return tokenDto;
    }

//    public void logout(){}

}
