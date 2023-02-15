package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.UserResDTO;
import Maswillaeng.MSLback.dto.user.request.UserJoinReqDTO;
import Maswillaeng.MSLback.dto.user.request.UserUpdateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserJoinReqDTO userJoinReqDTO) throws Exception {
        User user = userJoinReqDTO.toEntity();
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateReqDTO userUpdateReqDTO) {
        User user = userRepository.findById(userId).get();
        user.update(userUpdateReqDTO);
        userRepository.save(user);
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNickNameDuplicate(String nickName) {
        return userRepository.existsByEmail(nickName);
    }

    @Transactional(readOnly = true)
    public UserResDTO getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserResDTO(user);
    }

    public void userWithDraw(Long userId) {
        User user = userRepository.findById(userId).get();
        user.withdraw();
        userRepository.save(user);
    }
}
