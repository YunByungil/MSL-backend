package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.post.PostRepository;
import Maswillaeng.MSLback.domain.repository.user.UserRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.utils.AESEncryption;
import Maswillaeng.MSLback.utils.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostRepository postRepository;

    private final AESEncryption aesEncryption;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsBynickName(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    public boolean joinDuplicate(UserJoinRequestDto user) {
        boolean emailDuplicated =   userRepository.existsByEmail(user.getEmail());
        boolean nickNameDuplicated =   userRepository.existsByNickName(user.getNickName());
        return emailDuplicated || nickNameDuplicated;
    }

    public void join(UserJoinRequestDto requestDto) throws Exception {
       String encodePw =  aesEncryption.encrypt(requestDto.getPassword());
       User user = requestDto.toEntity(encodePw);
        userRepository.save(user);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) throws Exception {
        User selectUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String decodePw = aesEncryption.decrypt(selectUser.getPassword());
        if(!requestDto.getPassword().equals(decodePw)){
            throw new Exception("비밀번호가 일치하지 않습니다");
        }else if(selectUser.getWithdrawYn() == 1){
            throw new Exception("탈퇴한 회원 입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(selectUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(selectUser);
        selectUser.updateRefreshToken(refreshToken);
        userRepository.save(selectUser);

        TokenResponseDto token = TokenResponseDto.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
        return  LoginResponseDto.builder().tokenResponse(token).nickName(selectUser.getNickName()).userImage(selectUser.getUserImage()).build();


    }

    @Transactional
    public TokenResponseDto updateAccessToken(String access_token, String refresh_token) throws Exception {
        String updateAccessToken;

           // Claims claimsToken =  jwtTokenProvider.getRefreshClaims(refresh_token);
            Long userId = jwtTokenProvider.getUserId(refresh_token);
            Optional<User> user=  userRepository.findById(userId);
            String OriginalRefreshToken = user.get().getRefreshToken();
            if(OriginalRefreshToken.equals(refresh_token)){
                updateAccessToken = jwtTokenProvider.createAccessToken(user.get());
            }else{
                user.get().destroyRefreshToken();
                userRepository.save(user.get());
                throw new Exception("변조된 토큰");
            }

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updateAccessToken)
                .REFRESH_TOKEN(refresh_token)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserResponseDto(user);

    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User selectedUser = userRepository.findById(userId).get();

        selectedUser.update(requestDto);
        userRepository.save(selectedUser);
    }

    // 이 기능이 userService에 있는게 맞나?
    @Transactional(readOnly = true)
    public  Page<PostResponseDto> userPostList(Long userId,Pageable pageable){
         Page<Post> userPost =  postRepository.findAllByUser(userId,pageable);
        Page<PostResponseDto> postList = PageableExecutionUtils.getPage(userPost.getContent().stream().map(p -> new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getThumbNail(), p.getModifiedAt())).collect(Collectors.toList()), pageable, ()->userPost.getTotalElements());
            return postList;
        }

     @Transactional
    public void userWithDraw(Long userId) {
        User selectedUser = userRepository.findById(userId).get();
        selectedUser.withdraw();
        userRepository.save(selectedUser);
    }
}

