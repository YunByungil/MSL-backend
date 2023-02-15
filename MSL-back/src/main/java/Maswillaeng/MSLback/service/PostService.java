package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.PostResDTO;
import Maswillaeng.MSLback.dto.user.request.PostUpdateReqDTO;
import Maswillaeng.MSLback.dto.user.request.PostWriteReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(Long userId, PostWriteReqDTO postWriteReqDTO) {
        User user = userRepository.findById(userId).get();
        postRepository.save(postWriteReqDTO.toEntity(user));
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateReqDTO postUpdateReqDTO) {
        Post post = postRepository.findById(postId).get();
        post.update(postUpdateReqDTO);
    }

    @Transactional
    public PostResDTO getPost(Long postId) {
        Post post = postRepository.findById(postId).get();
        return new PostResDTO(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
