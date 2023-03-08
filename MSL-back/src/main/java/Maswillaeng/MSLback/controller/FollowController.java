package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/{userId}/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followerId}")
    public ResponseEntity addFollow(@PathVariable("userId") Long userId, @PathVariable Long followerId) {

        followService.follow(userId, followerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{followerId}")
    public ResponseEntity unFollow(@PathVariable("userId") Long userId, @PathVariable Long followerId) {
        followService.unfollow(userId, followerId);
        return ResponseEntity.ok().build();
    }
}