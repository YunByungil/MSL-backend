package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.post.response.PostListDto;
import Maswillaeng.MSLback.dto.post.response.PostSingleDto;
import Maswillaeng.MSLback.dto.post.request.PostFixDto;
import Maswillaeng.MSLback.dto.post.request.PostWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional // 전체 적용됌 ( option )
@RequiredArgsConstructor
public class PostService {
//필드주입을 권장하지 않음
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /*
     1. 생성
     */
    public void write(PostWriteDto writeDto, User user){
        //카테고리는 아예 front에서 넣어주면 안되는 건가?
        Post write = new Post();
            write.setUser_id(user.getId());
            write.setContent(writeDto.getContent());
            write.setTitle(writeDto.getTitle());
            write.setThumbnail(writeDto.getThumbnail());
        postRepository.save(write);
    }

    /*
     2. 수정
     */
    public void fix(PostFixDto fixDto, Post fixPost){
        fixPost.setContent(fixDto.getContent());
        fixPost.setTitle(fixDto.getTitle());
        fixPost.setThumbnail(fixDto.getThumbnail());
        postRepository.save(fixPost);
        //더티체킹으로 auto 가능
    }

    /*
     3. 삭제
     */
    public void deletePost (Post post){
        postRepository.delete(post);
    }

    /*
     4. 단일조회
     */
    public PostSingleDto selectSinglePost (Post post, User user){
        PostSingleDto singleDto = new PostSingleDto();
        singleDto.setPost_id(post.getPost_id());
        singleDto.setThumbnail(post.getThumbnail());
        singleDto.setTitle(post.getTitle());
        singleDto.setContent(post.getContent());

        singleDto.setUserimage(user.getUserimage());
        singleDto.setUserimage(user.getUserimage());

        return singleDto;
    }


    /*
     5. 전체조회
     */
    public List<PostListDto> selectPostList (int currentPage, User user){
        Pageable pageable = PageRequest.of(currentPage, 10);
        Page<Post> postPage = postRepository.findAll(pageable); // 자체로 던져도 가능 ~! 구성 확인 할 것
        List<Post> list = postPage.getContent();
        List<PostListDto> postList = new ArrayList<>();
        list.forEach(match->{
            PostListDto unit = new PostListDto();
            unit.setNickname(match.getUser().getNickname());
            unit.setUserimage(match.getUser().getUserimage());
            unit.setPost_id(match.getPost_id());
            unit.setThumbnail(match.getThumbnail());
            unit.setTitle(match.getTitle());
            unit.setContent(match.getContent());
            postList.add(unit);
        });
        return postList;
    }

     /*
     6. 내 게시글만 조회
     */
     public List<Post>  selectMyPostList (int currentPage, User user){
         List<Post> check = user.getPostList();


         return null;
     }

    /*
     7. id 로 post 찾아줌
     */

    public Post findPost(Long post_id){
       Optional<Post> check = postRepository.findById(post_id);
       return check.get();
    }
}
