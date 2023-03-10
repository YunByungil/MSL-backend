package com.maswilaeng.service;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.PostHate;
import com.maswilaeng.domain.entity.PostLike;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.PostHateRepository;
import com.maswilaeng.domain.repository.PostLikeRepository;
import com.maswilaeng.domain.repository.PostRepository;
import com.maswilaeng.domain.repository.UserRepository;
import com.maswilaeng.dto.post.request.PostLikeRequestDto;
import com.maswilaeng.utils.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostHateRepository postHateRepository;

    public void insertLike(PostLikeRequestDto postLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Post post = postRepository.findById(postLikeRequestDto.getPostId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        //이미 좋아요 되어 있으면 에러 반환
        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new RuntimeException("이미 해당 회원은 좋아요를 눌렀습니다.");
        }

        //싫어요가 있을시 없애주기
        if (postHateRepository.findByUserAndPost(user, post).isPresent()) {
            postRepository.subHateCount(post.getId());
            PostHate postHate = postHateRepository.findByUserAndPost(user, post).get();
            postHateRepository.delete(postHate);
        }

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        postLikeRepository.save(postLike);
        postRepository.addLikeCount(post.getId());
    }

    public void deleteLike(PostLikeRequestDto postLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Post post = postRepository.findById(postLikeRequestDto.getPostId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        // 좋아요가 눌려있지 않을 시
        PostLike postLike = postLikeRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 좋아요 정보입니다.")
        );

        postRepository.subLikeCount(post.getId());
        postLikeRepository.delete(postLike);
    }

    public void insertHate(PostLikeRequestDto postLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Post post = postRepository.findById(postLikeRequestDto.getPostId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        //이미 싫어요 되어 있으면 에러 반환
        if (postHateRepository.findByUserAndPost(user, post).isPresent()) {
            throw new RuntimeException("이미 해당 회원은 싫어요를 눌렀습니다.");
        }

        //좋아요가 있을시 없애주기
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            postRepository.subLikeCount(post.getId());
            PostLike postLike = postLikeRepository.findByUserAndPost(user, post).get();
            postLikeRepository.delete(postLike);
        }

        PostHate postHate = PostHate.builder()
                .post(post)
                .user(user)
                .build();

        postHateRepository.save(postHate);
        postRepository.addHateCount(post.getId());
    }

    public void deleteHate(PostLikeRequestDto postLikeRequestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        Post post = postRepository.findById(postLikeRequestDto.getPostId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다.")
        );

        // 싫어요가 눌려있지 않을 시
        PostHate postHate = postHateRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new EntityNotFoundException("싫어요가 눌려있지 않습니다.")
        );

        postRepository.subHateCount(post.getId());
        postHateRepository.delete(postHate);
    }
}
