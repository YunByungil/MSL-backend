package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Follow;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.FollowRepository;
import com.maswilaeng.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /** 팔로우 기능 */
    public void following(User toUser, User fromUser) throws Exception {

//        팔로우하고 있는지 확인 (팔로우 리스트 조인해서)
        if (!isFollowing(toUser, fromUser)) {
            //팔로우 하고 있지 않을 경우
            Follow follow = new Follow(toUser, fromUser);
            followRepository.save(follow);
        }
        else{
            throw new Exception("이미 팔로우를 눌렀습니다");
        }
    }


    /**
     * 팔로우 삭제
     */
    public void deleteFollow(User toUser, User fromUser) {
        if (isFollowing(toUser, fromUser)) {
            followRepository.deleteByToUserIdAndFromUserId(toUser.getId(), fromUser.getId());
        }
    }

    /**
     * 팔로우 여부를 확인
     */
    public boolean isFollowing(User toUser, User fromUser) {
        return followRepository.existsByToUser_IdAndFromUser_Id(toUser.getId(), fromUser.getId());
    }

    /**
     * 사용자가 팔로우 하는 목록 조회
     */
    public List<User> getFollowingList(User fromUser) {
        return followRepository.findToUsersByFromUserId(fromUser.getId());
    }

    /**
     * 사용자를 팔로우 하는 목록 조회
     */
    public List<User> getFollowerList(User toUser) {
        return followRepository.findFromUsersByToUserId(toUser.getId());
    }
}
