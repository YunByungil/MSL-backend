package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserSignDto;
import Maswillaeng.MSLback.dto.user.request.UserUpateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findIdUser(Long id){
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.get();
        return user;
    };

    public UserResponseDto selectMyInfo(User infoRes) {
        UserResponseDto responseDto = new UserResponseDto();
            responseDto.setEmail(infoRes.getEmail());
            responseDto.setNickname(infoRes.getNickname());
            responseDto.setPhoneNumber(infoRes.getPhonenumber());
            responseDto.setUserImage(infoRes.getUserimage());
            responseDto.setIntroduction(infoRes.getIntroduction());
        return responseDto;
    }

    public void save(UserSignDto signReq) {
        User signUser = new User();
             signUser.setEmail(signReq.getEmail());
             signUser.setPwd(signReq.getPwd());
             signUser.setNickname(signReq.getNickname());
             signUser.setPhonenumber(signReq.getPhoneNumber());
             signUser.setUserimage(signReq.getUserImage());
             signUser.setIntroduction(signReq.getIntroduction());
             signUser.setRole(User.userRole.BASIC);              //나중에 권한은 front에서 명시를 받아야함
             signUser.setWithdraw_yn(User.withDraw.N);
        userRepository.save(signUser);
    }

    public void updateMyInfo(Long id, UserUpateDto updateReq) {
        Optional<User> checkUser = userRepository.findById(id);
        User updateUser = checkUser.get();
             updateUser.setPwd(updateReq.getPwd());
             updateUser.setNickname(updateReq.getNickname());
             updateUser.setPhonenumber(updateReq.getPhoneNumber());
             updateUser.setUserimage(updateReq.getUserImage());
             updateUser.setIntroduction(updateReq.getIntroduction());
        userRepository.save(updateUser);
    }

    public void drawUser(User drawUser) {
        drawUser.setWithdraw_yn(User.withDraw.Y);
        userRepository.save(drawUser);
    }
}
