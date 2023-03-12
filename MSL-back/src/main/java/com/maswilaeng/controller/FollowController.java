package com.maswilaeng.controller;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.follow.request.FollowRequestDto;
import com.maswilaeng.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    /**
     * 팔로우
     */
    @PostMapping
    public ResponseEntity<?> follow(@RequestBody FollowRequestDto followRequestDto) {
        try {
            User toUser = userRepository.findById(followRequestDto.getToUserId()).orElseThrow(() -> new Exception("ToUser : 찾을수 없어용"));
            User fromUser = userRepository.findById(followRequestDto.getFromUserId()).orElseThrow(() -> new Exception("fromUser : 찾을수 없어용"));
            followService.following(toUser, fromUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 언팔로우
     */
    @DeleteMapping
    public ResponseEntity<?> unfollow(@RequestBody FollowRequestDto followRequestDto) throws Exception {
        User toUser = userRepository.findById(followRequestDto.getToUserId()).orElseThrow(() -> new Exception("ToUser : 찾을수 없어용"));
        User fromUser = userRepository.findById(followRequestDto.getFromUserId()).orElseThrow(() -> new Exception("fromUser : 찾을수 없어용"));
        followService.deleteFollow(toUser, fromUser);
        return ResponseEntity.ok().build();
    }

    /**
     * 팔로우 여부 확인
     */
    @GetMapping("/isFollowing")
    public ResponseEntity<?> isFollowing(@RequestParam Long toUserId, @RequestParam Long fromUserId) throws Exception {
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new Exception("ToUser : 찾을수 없어용"));
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new Exception("fromUser : 찾을수 없어용"));
        boolean isFollowing = followService.isFollowing(toUser, fromUser);
        return ResponseEntity.ok(isFollowing);
    }

    /**
     * 팔로잉 목록 조회
     */
    @GetMapping("/followingList")
    public ResponseEntity<?> followingList(@RequestParam Long fromUserId) throws Exception {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new Exception("fromUser : 찾을수 없어용"));
        List<User> followingList = followService.getFollowingList(fromUser);
        return ResponseEntity.ok(followingList);
    }

    /**
     * 팔로워 목록 조회
     */
    @GetMapping("/followerList")
    public ResponseEntity<?> followerList(@RequestParam Long toUserId) throws Exception {
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new Exception("ToUser : 찾을수 없어용"));
        List<User> followerList = followService.getFollowerList(toUser);
        return ResponseEntity.ok(followerList);
    }
}

