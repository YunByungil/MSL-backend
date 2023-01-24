package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.post.PostRepository;
import Maswillaeng.MSLback.domain.repository.user.UserRepository;
import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void save(PostSaveRequestDto post, String userToken) {
        Claims userClaims = jwtTokenProvider.getAccessClaims(userToken);
        Long userId = Long.parseLong(String.valueOf(userClaims.get("userId")));
        User user = userRepository.findById(userId).get();
        postRepository.save(post.toEntity(user));
    }
}
