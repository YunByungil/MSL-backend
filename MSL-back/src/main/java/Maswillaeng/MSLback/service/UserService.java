package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponse;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDTO;
import Maswillaeng.MSLback.dto.user.request.UserLoginRequestDto;
import Maswillaeng.MSLback.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final ValidateService validateService;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String secretKey; // 시크릿 키
    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L; // AccessToken 시간
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24L; // RefreshToken 시간

    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto dto) {
        /* 유저 존재 여부 확인 */
        User user = validateService.validateExistUser(dto);

        /* 비밀번호 맞는지 확인 */
        if(!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호 틀림");
        }

        String accessToken = JwtUtil.createJwt(user.getId(), user.getRole(), secretKey, ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = JwtUtil.createRefreshJwt(secretKey, REFRESH_TOKEN_EXPIRE_TIME);
        user.updateRefreshToken(refreshToken);
        System.out.println("refreshToken = " + refreshToken);
//        userRepository.save(user);
        TokenResponse token = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return UserLoginResponseDto.builder()
                .tokenResponse(token)
                .nickName(user.getNickname())
                .userImage(user.getUserImage())
                .build();
    }

    @Transactional
    public Long join(UserJoinDTO dto) {
//        validateDuplicateEmail(user.getEmail());
        User user = userRepository.save(dto.toEntity(encoder.encode(dto.getPassword())));
        return user.getId();
    }

//    @Transactional
//    public void update(Long userId, UserUpdateDTO userUpdateDTO) {
//        User user = userRepository.findOne(userId);
//        user.updateUser(userUpdateDTO);
//    }

//    public User findOne(Long userId) {
//        return userRepository.findOne(userId);
//    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

//    public void validateDuplicateEmail(String email) {
//        List<User> userEmail = userRepository.findByEmail(email);
//        if (!userEmail.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 Email입니다.");
//        }
//    }
}
