package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.FollowRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public void follow(Long userId, Long followerId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("유저이 존재하지 않습니다. id=" + userId)
        );
        User follower =  userRepository.findById(followerId).get();

        if(!followRepository.isFollow(userId, followerId)){
            Follow follow = Follow.builder().follower(user).following(follower).build();
            followRepository.save(follow);
        }
        else {
            throw new IllegalStateException("이미 구독중입니다");
        }
    }


    public void unfollow(Long userId, Long followerId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("유저이 존재하지 않습니다. id=" + userId)
        );
        Follow follow = followRepository.findByFollowId(userId, followerId).orElseThrow(
                ()-> new IllegalStateException("구독하지 않았습니다"));
        followRepository.delete(follow);
    }
}
