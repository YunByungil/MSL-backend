package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /*
    * Post 생성
    * */
    @Transactional
    public int createPost(PostRequestDto postRequestDto){
        postRepository.save(postRequestDto);
        return 200;
    }


    /*
     * Post 삭제
     * */

    @Transactional
    public int updatePost(PostRequestDto postRequestDto){
        postRepository.updatePost(postRequestDto);
        return 200;
    }

    /*
     * Post 단건 조회
     * */

    @Transactional
    public int findOne(Long recipe_id){
        postRepository.findOne(recipe_id);
        return 200;
    }

    /*
     * Post 목록 조회
     * */

    @Transactional
    public int findList(int pageNum){
        int firstNum, lastNum = 0;

        if(pageNum!=1){
            firstNum = (pageNum*10)+1;
            lastNum = (pageNum+1)*10;
        }else {
            firstNum = pageNum;
            lastNum = pageNum*10;
        }

        postRepository.findList(firstNum, lastNum );
        return 200;
    }
}
