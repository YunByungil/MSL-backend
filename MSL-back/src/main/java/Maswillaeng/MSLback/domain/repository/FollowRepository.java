package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowId(Long myId, Long userId);

    boolean isFollow(Long myId, Long userId);
}