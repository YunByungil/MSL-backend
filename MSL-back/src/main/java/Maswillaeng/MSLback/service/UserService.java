package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
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

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateDuplicateEmail(String email) {
        List<User> users = userRepository.findAll();
        if (users.contains(email)) {
            throw new IllegalStateException("이미 존재하는 Email입니다.");
        }
    }
}
