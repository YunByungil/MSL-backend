package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addPost(Long id, PostRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("없는 회원")
                );
        Post post = dto.toEntity(user);
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequestDto dto) {
        Post post = findOne(id);
        post.update(dto);
    }

    public Post findOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("게시글을 찾을 수 없습니다.")
                );

        return post;
    }
}
