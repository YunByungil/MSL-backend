package com.maswilaeng.jwt.service;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.user.request.LoginRequestDto;
import com.maswilaeng.dto.user.request.UserJoinDto;
import com.maswilaeng.jwt.dto.LoginResponseDto;
import com.maswilaeng.jwt.dto.TokenResponseDto;
import com.maswilaeng.jwt.entity.JwtTokenProvider;
import com.maswilaeng.jwt.entity.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.maswilaeng.jwt.entity.JwtTokenProvider.ACCESS_TOKEN_EXPIRE_TIME;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    public static final Long REFRESH_TOKEN_EXPIRE_TIME = Long.valueOf(1000 * 60 * 60 * 24 * 7); // 7일
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(UserJoinDto userJoinDto) throws Exception {
        String encryptedPw = passwordEncoder.encode(userJoinDto.getPassword());
        User user = userJoinDto.toUser();
        user.encryptPassword(encryptedPw);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        log.info("login 요청 들어옴! loginRequestDto.getemail(){}, loginRequestDto.getPassword(): {}", loginRequestDto.getEmail(), loginRequestDto.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        log.info("2단계 돌입전");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("authentication은 존재하는가 : {}", authentication);

        // 3. 인증 정보 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        log.info("JWT 토큰 생성 후 tokenInfo : {}", tokenInfo.toString());
        log.info("JWT 토큰 생성 후 authentication.getName : {}", authentication.getName());

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                        .tokenResponseDto(TokenResponseDto.builder()
                                .ACCESS_TOKEN(tokenInfo.getAccessToken())
                                .REFRESH_TOKEN(tokenInfo.getRefreshToken())
                                .build())
                        .nickName(user.getNickName())
                        .userImage(user.getUserImage())
                        .build();
        return loginResponseDto;
    }
/** 재발급
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다");
        }

        // 2. Access Token 에서 User Id를 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 User Id를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 이 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        //5. 새로운 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        //6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        //7. 토큰 발급
        return tokenDto;
    }
*/
//
//    public TokenResponseDto updateAccessToken(String refreshToken) {
//        User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
//        String OriginalRefreshToken = user.getRefreshToken();
//
//        String updatedAccessToken;
//        if (!tokenProvider.validateToken(OriginalRefreshToken)) {
//            throw new RuntimeException("Refresh Token 이 유효하지 않습니다");
//        } else {
//            updatedAccessToken = tokenProvider.generateAccessToken(user.getId(), user.getRole());
//        }
//
//        return TokenResponseDto.builder()
//                .ACCESS_TOKEN(updatedAccessToken)
//                .build();
//    }


    public boolean notDuplicate(User user) {
        return userRepository.existsByNickName(user.getNickName()) ||
                userRepository.existsByEmail(user.getEmail());
    }

    public void removeRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        user.destroyRefreshToken();
    }

    public ResponseCookie getAccessTokenCookie(String accessToken) {

        return ResponseCookie
                .from("ACCESS_TOKEN", accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(ACCESS_TOKEN_EXPIRE_TIME)
                .sameSite("Lax") // 쿠키 scope limitation -> cross-site: "Lax" same, 한 사이트: "Strict"
                .build();
    }

    public ResponseCookie getRefreshTokenCookie(String refreshToken) {
        return ResponseCookie
                .from("REFRESH_TOKEN", refreshToken)
                .httpOnly(true)
                .path("/updateToken")
                .maxAge(REFRESH_TOKEN_EXPIRE_TIME)
                .sameSite("Lax")
                .build();
    }


}
