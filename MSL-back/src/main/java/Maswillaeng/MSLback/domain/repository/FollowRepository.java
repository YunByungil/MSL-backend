package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    /**
     * 팔로우, 팔로워 개수 확인
     * @param userId
     * @return
     */
    Long countByFollow(Long userId);
    Long countByFollower(Long userId);

    /**
     * 팔로우, 팔로워 목록 불러오기
     * 존재하지 않을 경우, null Exception을 방지하기 위해 List를 사용.
     */
    List<Follow> getFollowList(Long userId);
    List<Follow> getFollowerList(Long userId);
}
