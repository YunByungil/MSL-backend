package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.Util.PasswordEncoder;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.auth.request.UserPasswordCheckRequestDto;
import Maswillaeng.MSLback.dto.auth.response.TokenResponseDto;
import Maswillaeng.MSLback.dto.auth.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.auth.request.UserLoginRequestDto;
import Maswillaeng.MSLback.Util.TokenProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static Maswillaeng.MSLback.Util.AuthConstants.ACCESS_EXPIRE;
import static Maswillaeng.MSLback.Util.AuthConstants.BEARER_PREFIX;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional //하나하나 지정하기
public class AuthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public boolean nicknameDuplicate(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public boolean emailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean joinDuplicate(UserJoinRequestDto userJoinDto) {
        return nicknameDuplicate(userJoinDto.getNickname()) || emailDuplicate(userJoinDto.getEmail());
    }

    public void join(UserJoinRequestDto requestDto) throws Exception {
        String encryptPw = encryptPassword(requestDto.getPassword());
        User user = requestDto.toEntity(encryptPw);

        userRepository.save(user);
    }

    public TokenResponseDto login(UserLoginRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL입니다."));
        String encryptPw = encryptPassword(requestDto.getPassword());
        if (encryptPw.equals(user.getPassword())) {
            return createToken(user);
        } else throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
    }

    public Boolean checkPassword(Long userId, UserPasswordCheckRequestDto requestDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        String encryptPw = encryptPassword(requestDto.getPassword());
        if (encryptPw.equals(user.getPassword())) {
            return true;
        }
        return false;
//        else throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
    }
    public void logout(Long userId){
        User user = userRepository.findById(userId).get();
        user.deleteRefreshToken();
    }
    public TokenResponseDto reissueToken(String refreshToken) {//if 너무많어
        Long userId = tokenProvider.decode(refreshToken);
        User user = userRepository.findById(userId).get();
        String userRefreshToken = user.getRefreshToken();

        if(tokenProvider.isValidRefreshToken(userRefreshToken)) { //들어온 Refresh 토큰이 유효한지
            //Refresh 토큰은 유효함
            if(refreshToken.equals(userRefreshToken)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                //Access 토큰 재발급 완료
                return createToken(user);
            }
            else{
                //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                user.deleteRefreshToken();
                userRepository.save(user);
                //예외발생
                throw new JwtException("Refresh 토큰이 변조되었습니다.");
            }
        }
        else{
            //Re-login request
            log.info("refresh token expired");
        }
        return null;
    }

    public TokenResponseDto createToken(User user){
        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);

        user.updateRefreshToken(refreshToken);

        return TokenResponseDto.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .tokenType(BEARER_PREFIX)
                .expires_in(ACCESS_EXPIRE)
                .refreshToken(refreshToken).build();
    }
    public String createAccessToken(User user) {
        return tokenProvider.createAccessToken(user);
    }

    public String createRefreshToken(User user) {
        return tokenProvider.createRefreshToken(user);
    }



    public String encryptPassword(String password) throws Exception {
        return passwordEncoder.encode(password);
    }
}
