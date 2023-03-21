package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;


//    public List<User> findAllUsers(){
//        return userRepository.findAll();
//    }


    public UserResponseDto findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserResponseDto.builder().user(user).build();
    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).get();
        user.update(requestDto);
    }


    public void deleteByUserId(Long userId){
        userRepository.deleteById(userId);
    }
}