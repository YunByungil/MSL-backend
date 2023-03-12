package com.maswilaeng.service;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.FollowRepository;
import com.maswilaeng.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /** 구독 기능 */
    public void following(Long userId, Long followingUserId) {
        // 팔로우하고 있는지 확인 (팔로우 리스트 조인해서)
//        User user = userRepository.findIfFollowingById(userId);//팔로우 대상 유저


    }
}
