package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.repository.post.query.PostTestRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostDetailResponse;
import Maswillaeng.MSLback.dto.post.reponse.PostListResponse;
import Maswillaeng.MSLback.dto.post.reponse.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponse;
import Maswillaeng.MSLback.dto.post.request.*;
import Maswillaeng.MSLback.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final PostTestRepository postTestRepository;
    @GetMapping()
    public String index() {
        return "index";
    }

    /**
     * 글쓰기
     * @param authentication
     * @param postRequestDto
     * TODO: 카테고리
     * @return
     */
    @PostMapping("/post")
    public PostResponse addPost(Authentication authentication, @RequestBody PostRequestDto postRequestDto) {
        // TODO: @Valid

        log.info(authentication.getName());
        System.out.println("id = " + authentication.getName());
        Long id = Long.parseLong(authentication.getName());
        log.info("content = {}", postRequestDto.getContent());
        postService.addPost(id, postRequestDto);

        return new PostResponse(HttpStatus.OK.value());
    }


    /**
     * 게시글 수정
     * @param postId
     * @param dto
     * @return
     */
    @PutMapping("/post/{postId}")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto dto) {
        postService.updatePost(postId, dto);
        return new PostResponse(HttpStatus.OK.value());
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/post/{postId}")
    public PostDetailResponse getPost(@PathVariable Long postId) {
        PostDetailDto dto = postService.getPost(postId);
//        PostDetailDto dto = new PostDetailDto(post);
        return new PostDetailResponse(HttpStatus.OK.value(), dto);
    }

    /**
     * 게시글 전체 조회
     * TODO: 이것도 Post로 받는게 아니라, Dto로 받아야 된다.
     */
//    @GetMapping("/post/page")
//    public PostListResponse getAllPost() {
//        List<Post> allPost = postService.getAllPost();
//        List<PostListDto> collect = allPost.stream()
//                .map(p -> new PostListDto(p.getId(), p.getUser().getNickname(), p.getThumbnail(), p.getTitle()))
//                .collect(Collectors.toList());
//
//        return new PostListResponse(collect.size(), HttpStatus.OK.value(), collect);
//    }

    /**
     * 연습용
     */
    @GetMapping("/post/page")
    public PostListResponse getAllPost(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "limit", defaultValue = "100") int limit) {
//        List<PostListResponseDto> dto = postService.testAllPost();
        List<PostListResponseDto> dto2 = postTestRepository.test(offset, limit);
//        List<Post> dto2 = postTestRepository.fetchJoin();
        return new PostListResponse(dto2.size(), HttpStatus.OK.value(), dto2);
    }
//    @GetMapping("/post/page")
//    public PostListResponse getAllPost() {
//        List<PostListResponseDto> dto = postService.testAllPost();
//
//        return new PostListResponse(dto.size(), HttpStatus.OK.value(), dto);
//    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{postId}")
    public PostResponse deletePost(Authentication authentication, @PathVariable Long postId) {
        /*
        대체 왜 /post/{postId}로 하면 1번 게시글이 삭제가 안 될까..
         */
        log.info("== deletePost Method ==");
        log.info("deletePost Method");
        log.info("deletePost, postId = {}", postId);
        log.info("user = {}", authentication.getName());
        log.info("user = {}", authentication.getName());
        log.info("user = {}", authentication.getName());
        log.info("user = {}", authentication.getName());
        log.info("userId = {}", Long.parseLong(authentication.getName()));
        Long myId = Long.parseLong(authentication.getName());
        postService.deletePost(postId, myId);
        return new PostResponse(HttpStatus.OK.value());
    }
}
