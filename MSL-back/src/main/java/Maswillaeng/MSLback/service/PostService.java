package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.post.PostRepository;
import Maswillaeng.MSLback.domain.repository.user.UserRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public PostListResponseDto getPosts(int page) {
        List<Post> posts = postRepository.postList(page);
        List<PostResponseDto> postList = posts.stream().map(p -> new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getThumbNail(), p.getModifiedAt())).collect(Collectors.toList());
        int totalCount = (int) postRepository.count();
        return new PostListResponseDto(totalCount, postList);
    }

    public PostResponseDto getPost(Long postId) throws Exception {
        Optional<Post> selectedPost = Optional.ofNullable(postRepository.getPost(postId));

        if (selectedPost.isPresent()) {
            Post p = selectedPost.get();
            return new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getContent(), p.getContent(), p.getModifiedAt());
        } else
            throw new Exception("게시글이 존재하지 않습니다");
    }
}
