package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.request.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateEmail(user.getEmail());
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void update(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findOne(userId);
        user.updateUser(userUpdateDTO);
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateDuplicateEmail(String email) {
        List<User> userEmail = userRepository.findByEmail(email);
        if (!userEmail.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 Email입니다.");
        }
    }
}
