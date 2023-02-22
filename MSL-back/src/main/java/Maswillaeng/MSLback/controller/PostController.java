package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.post.response.PostListDto;
import Maswillaeng.MSLback.dto.post.response.PostSingleDto;
import Maswillaeng.MSLback.dto.post.request.PostFixDto;
import Maswillaeng.MSLback.dto.post.request.PostWriteDto;
import Maswillaeng.MSLback.service.PostService;
import Maswillaeng.MSLback.service.TokenService;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    private final  PostSingleDto postSingleDto;

    /*
     1. 내가 작성한 글 보기
     memo : 내게시글
     */
    @GetMapping("/post/mine/{id}")
    public void userPostList(@PathVariable("id") String id){

    }

    /*
     2. 생성
     memo : 추후에 accesstoken 수시로 호출하는 부분 리펙
     */
    @PostMapping("/post/{accesstoken}")
    public void writePost(@RequestBody PostWriteDto writeDto, @PathVariable("accesstoken") String accesstoken ){
        User user = userService.findIdUser(tokenService.parseJwt(accesstoken));
        if( user.getId() != null ){
            postService.write(writeDto, user);
        }
    }

    /*
     3. 수정
     */
    @PutMapping("/post/{accesstoken}")
    public void fixPost(@RequestBody PostFixDto fixDto, @PathVariable("accesstoken") String accesstoken ){
        User user = userService.findIdUser(tokenService.parseJwt(accesstoken));
        postService.fix(fixDto, postService.findPost(fixDto.getPost_id()));
    }

    /*
     4. 삭제
     */
    @DeleteMapping("/post/{post_id}/{accesstoken}")
    public void deletePost(@PathVariable("post_id") Long post_id, @PathVariable("accesstoken") String accesstoken ){
        User user = userService.findIdUser(tokenService.parseJwt(accesstoken));
        if( user.getId() != null ){
            postService.deletePost(postService.findPost(post_id));
        }
    }

    /*
     5. 단일 조회
     */
    @GetMapping("/post/{post_id}/{accesstoken}")
    public ResponseEntity<PostSingleDto> selectSinglePost(@PathVariable("post_id") Long post_id, @PathVariable("accesstoken") String accesstoken){
        User user = userService.findIdUser(tokenService.parseJwt(accesstoken));
        if( user.getId() != null ){
            postService.selectSinglePost(postService.findPost(post_id), user);
            return new ResponseEntity<>(postSingleDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(postSingleDto, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     6. 전체 조회
     */
    @GetMapping("/post/page/{currentPage}/{accesstoken}")
    public ResponseEntity<List<PostListDto>> selectPostList(@PathVariable("currentPage") int currentPage, @PathVariable("accesstoken") String accesstoken){
        User user = userService.findIdUser(tokenService.parseJwt(accesstoken));
        if( user.getId() != null ){
            List<PostListDto> list = postService.selectPostList(currentPage, user);
            return ResponseEntity.ok(list);
        }else {
            return null;
        }
    }


}
