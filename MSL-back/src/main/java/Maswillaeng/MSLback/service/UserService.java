package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void join(User user) {
        userRepository.save(user);
    }

    public void update(Long userId, UserUpdateRequestDto requestDto) {
        User needToUpdate = userRepository.findById(userId).get();
        needToUpdate.update(requestDto);
    }

    public boolean duplicate(User user) {
        return userRepository.existsByNickName(user.getNickName()) ||
                userRepository.existsByEmail(user.getEmail());
    }
}