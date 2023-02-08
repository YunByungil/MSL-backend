package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickname);
}
