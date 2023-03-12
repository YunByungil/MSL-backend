package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.post.request.PostRequestDto;
import com.maswilaeng.dto.post.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /* CREATE */
    @Transactional
    public Long save(PostRequestDto dto, String nickName) {

        /* User 정보를 가져와 dto에 담아준다. */
        User user = userRepository.findByNickName(nickName);
        dto.setUser_id(user.getId());
        Post post = dto.toEntity();
        postRepository.save(post);

        return post.getId();
    }

    /* READ 게시글 리스트 조회 */
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostById(Long id) {
        List<Post> post = postRepository.findByPostId(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new PostResponseDto(post.stream().));
    }

    /*
     * UPDATE -> dirty checking 으로 하는지?
     */
    @Transactional
    public void update(Long id, PostRequestDto dto) { // id 없는 객체 -> null "mergeX"
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
//
//    /* search */
//    @Transactional(readOnly = true)
//    public Page<Post> search(String keyword, Pageable pageable) {
//        Page<Post> postList = postRepository.findByTitleContaining(keyword, pageable);
//        return postList;
//    }
}
