package com.maswilaeng.jwt.service;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.user.request.LoginRequestDto;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.jwt.TokenProvider;
import com.maswilaeng.jwt.dto.TokenDto;
import com.maswilaeng.jwt.dto.TokenRequestDto;
import com.maswilaeng.jwt.dto.TokenResponseDto;
import com.maswilaeng.jwt.entity.RefreshToken;
import com.maswilaeng.jwt.resository.RefreshTokenRepository;
import com.maswilaeng.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(UserJoinDto userJoinDto) {
        User user = userJoinDto.toUser(passwordEncoder);
        userRepository.save(user);
    }


    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        } else if (user.getWithdrawYn() == 1) {
            throw new EntityNotFoundException("탈퇴한 회원입니다.");
        }

        //1. Login ID/PW를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //      authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만든 loadUserByUsername이 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다");
        }

        // 2. Access Token 에서 User Id를 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 User Id를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 이 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        //5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        //6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        //7. 토큰 발급
        return tokenDto;
    }


    public TokenResponseDto updateAccessToken(String refreshToken) {
        User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
        String OriginalRefreshToken = user.getRefreshToken();

        String updatedAccessToken;
        if (!tokenProvider.validateToken(OriginalRefreshToken)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다");
        } else {
            updatedAccessToken = tokenProvider.generateAccessToken(user.getId(), user.getRole());
        }

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updatedAccessToken)
                .build();
    }


    public boolean joinDuplicate(User user) {
        return userRepository.existsByNickName(user.getNickName()) ||
                userRepository.existsByEmail(user.getEmail());
    }

    public void removeRefreshToken(Long userId) {

        User user = userRepository.findById(userId).get();
        user.destroyRefreshToken();
    }
}
