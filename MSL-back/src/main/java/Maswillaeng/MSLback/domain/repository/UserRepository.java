package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//JpaRepository 를 사용하면 PertsistenceContext, Manager 자동 생성되는거 같음
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
}
