package com.maswilaeng.service;

import com.maswilaeng.Domain.entity.Post;
import com.maswilaeng.Domain.entity.User;
import com.maswilaeng.Domain.repository.PostRepository;
import com.maswilaeng.Domain.repository.UserRepository;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /* CREATE */
    @Transactional
    public Long save(PostRequestDto dto, String nickname) {
        /* User 정보를 가져와 dto에 담아준다. */
        User user = userRepository.findByNickname(nickname);
        dto.setUser_id(user.getUser_id());
        log.info("PostService save() 실행");
        Post post = dto.toEntity();
        postRepository.save(post);

        return post.getPost_id();
    }

    /* READ 게시글 리스트 조회 readOnly 속성으로 조회속도 개선 */
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new PostResponseDto(post);
    }

    /*
     * UPDATE (dirty checking 영속성 컨텍스트)
     */
    @Transactional
    public void update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        post.update(dto.getTitle(), dto.getContent());
    }

    /* DELETE */
    @Transactional
    public void delete(Long id) {
        Post posts = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        postRepository.delete(posts);
    }

    /* search */
    @Transactional(readOnly = true)
    public Page<Post> search(String keyword, Pageable pageable) {
        Page<Post> postList = postRepository.findByTitleContaining(keyword, pageable);
        return postList;
    }
}
