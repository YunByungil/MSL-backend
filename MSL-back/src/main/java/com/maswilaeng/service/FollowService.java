package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Follow;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.FollowRepository;
import com.maswilaeng.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public void createFollow(Long toUserId, Long fromUserId) throws Exception {

//        팔로우하고 있는지 확인 (팔로우 리스트 조인해서)
        if (!isFollowing(toUserId, fromUserId)) {
            //팔로우 하고 있지 않을 경우
            User toUser = userRepository.findById(toUserId).orElseThrow(
                    () -> new UsernameNotFoundException("toUser를 찾을 수 없습니다")
            );
            User fromUser = userRepository.findById(fromUserId).orElseThrow(
                    () -> new UsernameNotFoundException("fromUser를 찾을 수 없습니다")
            );
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
    public void deleteFollow(Long toUserId, Long fromUserId) {
        if (isFollowing(toUserId, fromUserId)) {
            followRepository.deleteByToUserIdAndFromUserId(toUserId, fromUserId);
        }
    }

    /**
     * 팔로우 여부 확인
     */
    public boolean isFollowing(Long toUserId, Long fromUserId) {
        return followRepository.existsByToUser_IdAndFromUser_Id(toUserId, fromUserId);
    }

    /**
     * 사용자가 팔로우 하는 목록 조회(fetch join)
     */
    public List<Follow> getFollowingList(Long fromUserId) {
        return followRepository.findJoinToUsersByFromUserId(fromUserId);
    }

    /**
     * 사용자를 팔로우 하는 목록 조회(fetch join)
     */
    public List<Follow> getFollowerList(Long toUserId) {
        return followRepository.findJoinFromUsersByToUserId(toUserId);
    }
////
//    /**
//     * 사용자가 팔로우 하는 목록 조회
//     */
//    public List<User> getFollowingListNoJoin(User fromUser) {
//            return followRepository.findToUsersByFromUserId(fromUser.getId());
//    }
//
//    /**
//     * 사용자를 팔로우 하는 목록 조회
//     */
//    public List<User> getFollowerListNoJoin(User toUser) {
//        return followRepository.findFromUsersByToUserId(toUser.getId());
//    }
}
