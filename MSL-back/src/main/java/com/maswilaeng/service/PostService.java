package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.request.PostUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).get();
        postRepository.save(postRequestDto.toEntity(user));
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new EntityNotFoundException("해당 게시글이 존재하지 않습니다. id: " + postId));
    }


    @Transactional
    public void updatePost(Long userId, PostUpdateDto updateDto) throws Exception { // id 없는 객체 -> null "mergeX"

        Post toUpdatePost = postRepository.findById(updateDto.getId()).get();

        if (Objects.equals(toUpdatePost.getUser().getId(), userId)) {
            toUpdatePost.update(updateDto);
        } else{
            throw new Exception("해당 게시물 접근 권한 없음");
        }

    }

    /* DELETE */
    @Transactional
    public void delete(Long userId, Long postId) throws ValidationException {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + postId));

        if (!Objects.equals(userId, post.getUser().getId())) {
            throw new ValidationException("접근 권한 없음");
        }
        postRepository.delete(post);
    }
//
//    public UserPostListResponseDto getUserPostList(Long userId) {
//        return new UserPostListResponseDto(postRepository.findAllPostByUserId(userId));
//    }

//    /* search */
//    @Transactional(readOnly = true)
//    public Page<Post> search(String keyword, Pageable pageable) {
//        Page<Post> postList = postRepository.findByTitleContaining(keyword, pageable);
//        return postList;
//    }
}
