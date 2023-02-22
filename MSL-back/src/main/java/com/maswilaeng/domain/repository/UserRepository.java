package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByNickName(String nickName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // 쿼리 수행시 Eager조회. authorities 정보 같이 가져옴
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    boolean existsByNickName(String nickName);
}
