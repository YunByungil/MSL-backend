package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.post.PostRepository;
import Maswillaeng.MSLback.domain.repository.user.UserRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostUpdateResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public PostService(PostRepository postRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public void save(PostSaveRequestDto post, String userToken) {
        Long userId = jwtTokenProvider.getUserId(userToken);
        //TODO: userId 있는 지 확인 ?
        User user = userRepository.findById(userId).get();
        postRepository.save(post.toEntity(user));
    }

    public Page<PostResponseDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.postList(pageable);

        Page<PostResponseDto> postList = PageableExecutionUtils.getPage(posts.getContent().stream().map(p -> new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getThumbNail(), p.getModifiedAt())).collect(Collectors.toList()), pageable, ()->posts.getTotalElements());
      //  int totalCount = (int) postRepository.count();
        return postList;
    }

    public PostResponseDto getPost(Long postId) throws Exception {
        Optional<Post> selectedPost = Optional.ofNullable(postRepository.getPost(postId));
      //  postRepository.getPost()

        if (selectedPost.isPresent()) {
            Post p = selectedPost.get();
            return new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getContent(), p.getContent(), p.getModifiedAt());
        } else
            throw new Exception("게시글이 존재하지 않습니다");
    }

    @Transactional
    public PostUpdateResponseDto updatedPost(Long postId, String userToken, PostUpdateRequestDto requestDto) throws Exception {
        Long userId = jwtTokenProvider.getUserId(userToken);

        Post selectedPost = postRepository.findById(postId).get();

        if (selectedPost.getUser().getUser_id()==(userId)) {
            selectedPost.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getThumbNail());
            Post updatedPost = postRepository.save(selectedPost);
            return new PostUpdateResponseDto(updatedPost);
        } else {
            throw new Exception("접근 권한 없음");
        }
    }

    @Transactional
    public void deletePost(Long postId, String userToken) throws Exception {
        Long userId = jwtTokenProvider.getUserId(userToken);

        Post selectedPost = postRepository.findById(postId).get();
        if (selectedPost.getUser().getUser_id()==(userId)) {
            postRepository.delete(selectedPost);
        }else {
            throw new Exception("접근권한없음");
        }
    }
}
