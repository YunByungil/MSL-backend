package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Follow;
import com.maswilaeng.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query("DELETE FROM Follow f WHERE f.toUser.id = :toUserId AND f.fromUser.id = :fromUserId")
    void deleteByToUserIdAndFromUserId(@Param("toUserId") Long toUserId, @Param("fromUserId") Long fromUserId);

    boolean existsByToUser_IdAndFromUser_Id(Long toUserId, Long fromUserId);

    /** 내가 팔로우 하고 있는 사람 목록 조회 (fetch join)*/
    @Query("SELECT f FROM Follow f join fetch f.toUser WHERE f.fromUser.id = :fromUserId")
    List<Follow> findJoinToUsersByFromUserId(@Param("fromUserId") Long fromUserId);

    /** 나를 팔로잉 하고 있는 사람 목록 조회 (fetch join)*/
    @Query("SELECT f FROM Follow f join fetch f.fromUser WHERE f.toUser.id = :toUserId")
    List<Follow> findJoinFromUsersByToUserId(@Param("toUserId") Long toUserId);
}
