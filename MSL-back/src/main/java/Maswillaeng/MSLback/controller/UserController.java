package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.Util.AuthenticationPrincipal;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.post.response.PostListResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findByUserId(@PathVariable Long userId){
        UserResponseDto userDto = userService.findByUserId(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@AuthenticationPrincipal Long userId, @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteByUserId(@AuthenticationPrincipal Long userId) {
        userService.deleteByUserId(userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> userImageUpdate(@RequestParam("photo") MultipartFile imageFile) throws IOException {

        return ResponseEntity.ok().body(userService.uploadUserImage(imageFile));
    }
}
