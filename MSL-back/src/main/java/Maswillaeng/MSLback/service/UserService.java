package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.Util.TokenProvider;
import Maswillaeng.MSLback.domain.entity.RefreshToken;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.RefreshTokenRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.TokenDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserLoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public void join(UserJoinRequestDto userJoinDto){
        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public TokenDto login(UserLoginRequestDto requestDto) {
//        User user = userRepository.findByEmail(requestDto.getEmail()).get();
//        if(requestDto.getEmail() == null || requestDto.getPassword() == null)
//            return null;
//        String encodePw = requestDto.getPassword();
//        if(passwordEncoder.matches(requestDto.getPassword(),encodePw)) {
//            //암호화 된 비밀번호로 pw 정보 변경 후 로그인
//        }else {
//            return null;
        TokenDto tokenDto = tokenProvider.generateTokenDto(requestDto.toString());
        RefreshToken refreshToken = RefreshToken.builder().build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    public boolean nicknameDuplicate(String nickname){
        return userRepository.existsByNickName(nickname);
    }

    public boolean emailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean joinDuplicate(UserJoinRequestDto userJoinDto) {
        return nicknameDuplicate(userJoinDto.getNickname()) || emailDuplicate(userJoinDto.getEmail());
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).get();
        user.update(requestDto);
    }


    public void deleteByUserId(Long userId){
        userRepository.deleteById(userId);
    }
}