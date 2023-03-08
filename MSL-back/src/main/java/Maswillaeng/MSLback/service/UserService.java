package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.auth.request.UserJoinRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;


    public boolean nicknameDuplicate(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public boolean emailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean joinDuplicate(UserJoinRequestDto userJoinDto) {
        return nicknameDuplicate(userJoinDto.getNickname()) || emailDuplicate(userJoinDto.getEmail());
    }

//    public List<User> findAllUsers(){
//        return userRepository.findAll();
//    }

    public Optional<User> findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).get();
        user.update(requestDto);
    }


    public void deleteByUserId(Long userId){
        userRepository.deleteById(userId);
    }
}