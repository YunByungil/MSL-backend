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

    boolean existsByNickName(String nickName);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    @Query("select u from User u left join fetch u.followingList f1 left join fetch u.followerList f2 where u.id =:userId")
    User findIfFollowingById(@Param("userId") Long userId);

    @Query("select u from User u left join fetch u.followerList f where u.id =:userId")
    User findIfFollowedById(@Param("userId")Long userId);

}
