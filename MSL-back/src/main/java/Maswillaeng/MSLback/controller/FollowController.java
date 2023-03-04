package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/{userId}/follow")
    public ResponseEntity addFollow(@PathVariable("userId") Long userId, Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        followService.addFollow(myId, userId);
        return ResponseEntity.ok().body("팔로우 완료!");
    }
}
