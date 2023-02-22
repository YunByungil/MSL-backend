package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.TokenIndexResponse;
import Maswillaeng.MSLback.dto.auth.response.UserTokenResponseDto;
import Maswillaeng.MSLback.dto.auth.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.auth.request.UserLoginRequestDto;
import Maswillaeng.MSLback.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public void join(UserJoinRequestDto userJoinDto){
        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    public UserTokenResponseDto login(UserLoginRequestDto requestDto) throws IllegalAccessException {
//        if(requestDto.getEmail().equals(null) || requestDto.getPassword().equals(null)){
//            throw new IllegalAccessException("로그인 정보가 입력되지 않았습니다.");
//        }  //프론트에서 하는게 맞지않나?
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL입니다."));;

        String encodePw = passwordEncoder.encode(requestDto.getPassword());
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
        UserTokenResponseDto responseDto =  new UserTokenResponseDto(user.getId(),
                tokenProvider.createAccessToken(user), tokenProvider.createRefreshToken(user));

        // 5. 토큰 발급
        return responseDto;
    }


}
