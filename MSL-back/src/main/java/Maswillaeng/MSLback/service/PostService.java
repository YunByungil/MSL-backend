package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.post.PostRepository;
import Maswillaeng.MSLback.domain.repository.user.UserRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateRequestDto;
import Maswillaeng.MSLback.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public void save(PostSaveRequestDto post, Long userId) {
        User user = userRepository.findById(userId).get();
        postRepository.save(post.toEntity(user));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto>  getPosts(Pageable pageable) {
       Page<Post> posts = postRepository.findAll(pageable);
       Page<PostResponseDto> postList = PageableExecutionUtils.getPage(posts.getContent().stream().map(p -> new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getTitle(), p.getThumbNail(), p.getModifiedAt())).collect(Collectors.toList()), pageable, ()->posts.getTotalElements());
        return postList;
    }


    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) throws Exception {
        Optional<Post> selectedPost = postRepository.findPostsBy(postId);
        if (selectedPost.isPresent()) {
            Post p = selectedPost.get();
            return new PostResponseDto(p.getPostId(), p.getUser().getNickName(), p.getUser().getUserImage(), p.getTitle(), p.getContent(), p.getContent(), p.getModifiedAt());
        } else
            throw new Exception("게시글이 존재하지 않습니다");
    }

    @Transactional
    public void updatedPost(Long postId, Long userId, PostUpdateRequestDto requestDto) throws Exception {

        Post selectedPost = postRepository.findById(postId).get();

        if (selectedPost.getUser().getUser_id()==(userId)) {
            selectedPost.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getThumbNail());
           postRepository.save(selectedPost);
        } else {
            throw new Exception("접근 권한 없음");
        }
    }



    @Transactional
    public void deletePost(Long postId, Long userId) throws Exception {
        Post selectedPost = postRepository.findById(postId).get();
        if (selectedPost.getUser().getUser_id()==(userId)) {
            postRepository.delete(selectedPost);
        }else {
            throw new Exception("접근권한없음");
        }
    }

}
