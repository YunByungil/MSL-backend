package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.auth.request.UserTokenRequestDto;
import Maswillaeng.MSLback.dto.auth.response.UserTokenResponseDto;
import Maswillaeng.MSLback.dto.auth.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.auth.request.UserLoginRequestDto;
import Maswillaeng.MSLback.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public void join(UserJoinRequestDto userJoinDto){
        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        user.updateRole(RoleType.USER);
        userRepository.save(user);
    }

    public UserTokenResponseDto login(UserLoginRequestDto requestDto) throws IllegalAccessException {
//        if(requestDto.getEmail().equals(null) || requestDto.getPassword().equals(null)){
//            throw new IllegalAccessException("로그인 정보가 입력되지 않았습니다.");
//        }  //프론트에서 하는게 맞지않나?
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL입니다."));;

        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            String accessToken = tokenProvider.createAccessToken(user);
            String refreshToken = tokenProvider.createRefreshToken(user);

            user.updateRefreshToken(refreshToken);

            return UserTokenResponseDto.builder()
                    .id(user.getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken).build();
        }
        else throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
    }

    public void logout(HttpServletRequest request){
        String accessToken = request.getHeader("ACCESS_TOKEN");
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        Claims claimsToken = tokenProvider.getClaimsToken(refreshToken);
        Long userId = Long.valueOf(claimsToken.get("userId").toString());
        User user = userRepository.findById(userId).get();

        user.deleteRefreshToken();
    }
    public UserTokenResponseDto issueAccessToken(HttpServletRequest request) {//if 너무많어
        String accessToken = request.getHeader("ACCESS_TOKEN");
        String refreshToken = request.getHeader("REFRESH_TOKEN"); //Pk는 갖고있는게 맞다.
        Claims claimsToken = tokenProvider.getClaimsToken(refreshToken);
        Long userId = Long.valueOf(claimsToken.get("userId").toString());
        User user = userRepository.findById(userId).get();
        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급(refreshToken의 내용을 통해서)
//        if(!tokenProvider.isValidAccessToken(accessToken)){  //클라이언트에서 토큰 재발급 api로의 요청을 확정해주면 이 조건문은 필요없다.
//            //Access 토큰 만료됨
//            System.out.println("Access 토큰 만료됨");
//          }
        if(!tokenProvider.isValidRefreshToken(refreshToken)) { //들어온 Refresh 토큰이 유효한지
            //입력으로 들어온 Refresh 토큰이 유효하지 않음, 재로그인 요청

        }
        else{
            //Refresh 토큰은 유효함
            System.out.println("Refresh 토큰은 유효함");
            String tokenFromDB = user.getRefreshToken();
            System.out.println(refreshToken);
            System.out.println(tokenFromDB);
            if(refreshToken.equals(tokenFromDB)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                //Access 토큰 재발급 완료
                System.out.println("Access 토큰 재발급 완료");
                accessToken = tokenProvider.createAccessToken(user);
            }
            else{
                //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                System.out.println("Refresh Token Tampered");
                //예외발생
            }
        }
        return new UserTokenResponseDto(
                userId, accessToken, refreshToken);
    }

}
