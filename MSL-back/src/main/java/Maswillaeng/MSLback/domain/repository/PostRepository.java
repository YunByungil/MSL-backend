package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    // 1. 저장
    public void save(PostRequestDto postRequestDto){
        em.persist(postRequestDto);
    }


    //  2. 삭제
    // 게시글은 보관하고 삭제? 아니면 그냥삭제..? 보관 삭제면 update로 상태값 변환만 적용 아닐 경우 delete
    public void deletePost(Long recipe_id){ }

    //  3. 수정
    public void updatePost(PostRequestDto postRequestDto){
        em.persist(postRequestDto);
    }

    // 4-1. 상세조회
    public PostRequestDto findOne(Long recipe_id){
        return em.find(PostRequestDto.class, recipe_id);
    }

    // 4-2. 목록 조회
    //currentPage 처리 다시좀 고민해야함
    public List<PostRequestDto> findList(int firstNum, int lastNum){
        return em.createQuery("select p from PostRequestDto p LIMIT currentPage ", PostRequestDto.class)
                 .getResultList();
    }
}
