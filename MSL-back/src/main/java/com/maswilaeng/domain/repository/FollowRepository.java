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

    @Query("SELECT f.toUser FROM Follow f WHERE f.fromUser.id = :fromUserId")
    List<User> findToUsersByFromUserId(@Param("fromUserId") Long fromUserId);

    @Query("SELECT f.fromUser FROM Follow f WHERE f.toUser.id = :toUserId")
    List<User> findFromUsersByToUserId(@Param("toUserId") Long toUserId);
}
