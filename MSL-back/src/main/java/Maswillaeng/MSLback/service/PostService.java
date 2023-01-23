package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(Post post) {

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostList(int currentPage) {

        return postRepository.findAll(PageRequest.of(
                        currentPage - 1, 10, Sort.Direction.DESC, "createdAt"));
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 포스트를 찾지 못했습니다."));
    }

    public void updatePost(PostUpdateDto updateDto) {
        Post post = postRepository.findById(updateDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("해당 포스트를 찾지 못했습니다."));
        post.updatePost(updateDto);
    }
}
