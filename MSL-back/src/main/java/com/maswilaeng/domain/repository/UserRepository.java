package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // 쿼리 수행시 Eager조회. authorities 정보 같이 가져옴
//    @EntityGraph(attributePaths = "authorities")
//    Optional<User> findOneWithAuthoritiesByUserName(String username);

    boolean existsByNickName(String nickName);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    @Query("select u from User u left join fetch u.followerList where u.id =:userId")
    User findIfFollowingById(@Param("userId") Long userId);
}
