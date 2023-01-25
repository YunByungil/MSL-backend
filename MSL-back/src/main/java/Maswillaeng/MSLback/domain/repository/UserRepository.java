package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
