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
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostRepository postRepository;

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

    public void join(UserJoinRequestDto requestDto) {
       User user = requestDto.toEntity();
        userRepository.save(user);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) throws Exception {
        System.out.println("requset : " + requestDto.getPassword());
        User selectUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        System.out.println("userIDDDDD" +selectUser.getUser_id());
        System.out.println("user :" + selectUser.getPassword());
        if(!selectUser.getPassword().equals(requestDto.getPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다");
        }

        String accessToken = jwtTokenProvider.createAccessToken(selectUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(selectUser);
        selectUser.updateRefreshToken(refreshToken);
        userRepository.save(selectUser);
       // TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
        TokenResponseDto token = TokenResponseDto.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
        return  LoginResponseDto.builder().tokenResponse(token).nickName(selectUser.getNickName()).userImage(selectUser.getUserImage()).build();


    }

    @Transactional
    public TokenResponseDto updateAccessToken(String refresh_token, String access_token) throws Exception {
        String updateAccessToken;
        if(access_token!=null){
            Claims claimsToken =  jwtTokenProvider.getRefreshClaims(refresh_token);
            Long userId = (Long) claimsToken.get("userId");
            Optional<User> user=  userRepository.findById(userId);
            String OriginalRefreshToken = user.get().getRefreshToken();
            if(OriginalRefreshToken.equals(refresh_token)){
                updateAccessToken = jwtTokenProvider.createAccessToken(user.get());
            }else{
                user.get().destroyRefreshToken();
                userRepository.save(user.get());
                throw new Exception("변조된 토큰");
            }
        }else
            throw new Exception("access Token이 없습니다");

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updateAccessToken)
                .REFRESH_TOKEN(refresh_token)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(String userToken) {
//      Claims userClaims =  jwtTokenProvider.getAccessClaims(userToken);
//      Long userId = Long.parseLong(String.valueOf(userClaims.get("userId")));
        Long userId = jwtTokenProvider.getUserId(userToken);

        User user = userRepository.findById(userId).get();

        return new UserResponseDto(user);

    }

    @Transactional
    public UserResponseDto updateUser(String userToken, UserUpdateRequestDto requestDto) {
        Claims userClaims =  jwtTokenProvider.getAccessClaims(userToken);
        Long userId = Long.parseLong(String.valueOf(userClaims.get("userId")));

        User selectedUser = userRepository.findById(userId).get();

        selectedUser.update(requestDto);
        User updatedUser = userRepository.save(selectedUser);
        return new UserResponseDto(updatedUser);
    }

    // 이 기능이 userService에 있는게 맞나?
    @Transactional(readOnly = true)
    public  Page<PostResponseDto> userPostList(String userToken, Pageable pageable) throws Exception {
        Claims userClaims =  jwtTokenProvider.getAccessClaims(userToken);
        Long userId = Long.parseLong(String.valueOf(userClaims.get("userId")));

         Page<Post> userPost =  postRepository.userPostList(userId,pageable);
            Page<PostResponseDto> postList = PageableExecutionUtils.getPage(userPost.getContent().stream().map(p -> new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getThumbNail(), p.getModifiedAt())).collect(Collectors.toList()), pageable, ()->userPost.getTotalElements());
            return postList;
        }

     @Transactional
    public void userWithDraw(String userToken) {
        Claims userClaims =  jwtTokenProvider.getAccessClaims(userToken);
        Long userId = Long.parseLong(String.valueOf(userClaims.get("userId")));

        User selectedUser = userRepository.findById(userId).get();
        selectedUser.withdraw();
        userRepository.save(selectedUser);
    }
}

