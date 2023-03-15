package com.maswilaeng.service;

import com.maswilaeng.domain.entity.HashTag;
import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.Tag;
import com.maswilaeng.domain.repository.HashTagRepository;
import com.maswilaeng.domain.repository.TagRepository;
import com.maswilaeng.dto.post.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HashTagService {


    private static HashTagRepository hashTagRepository;
    private static TagRepository tagRepository;

    public Set<HashTag> saveHashTags(Set<String> Tags, Post post) {
        // Tags 는 글을 등록할 때 작성자가 쓴 해쉬태그들.
        return Tags.stream()
                .map(tagName -> findOrCreateTag(tagName)) //tagName에 맞는 Tag를 만들어주었고
                .map(tag -> new HashTag(tag, post))//만들어준 tag를 post와 엮어준다.
                .collect(Collectors.toSet());
   }

    public void deleteHashTags(Set<String> TagsToRemove, Post post) {
        hashTagRepository.deleteByPostId(post.getId());
        for (String tagName:TagsToRemove) {
            //post와 연결된 Tag가 아닐 경우에는 삭제
            if (hashTagRepository.findByTagName(tagName).size() == 0) {
                tagRepository.deleteByTagName(tagName);
            }
        }
    }

    public Set<HashTag> updateHashTag(Set<String> tagsToUpdate, Post post) {
        Set<HashTag> beforeUpdate = hashTagRepository.findByPost(post);
        Set<String> stringBeforeUpdateTags = beforeUpdate.stream().map(b -> b.getTag().getTagName())
                .collect(Collectors.toSet());

        //기존 태그 - 업데이트 : 삭제할 태그
        Set<String> tagsToDelete = stringBeforeUpdateTags.stream()
                .filter(tag -> tagsToUpdate.contains(tag))
                .collect(Collectors.toSet());

        //업데이트 태그 - 기존 태그 : 새로 생성할 태그
        Set<String> tagsToInsert = tagsToUpdate.stream()
                .filter(tag -> stringBeforeUpdateTags.contains(tag))
                .collect(Collectors.toSet());

        deleteHashTags(tagsToDelete ,post);
        return saveHashTags(tagsToInsert, post);
    }


    /**
     * Query를 엄청 보낼 것 같다.
     * TODO : List로 바꿔서 한번에 조회하기
     */
    private Tag findOrCreateTag(String tagName) {
        Tag tag = tagRepository.findTagByTagName(tagName);
        if (tag == null) {
            tag = Tag.builder()
                    .tagName(tagName)
                    .build();
        }
        return tagRepository.save(tag);
    }

    public List<PostResponseDto> findPostByHashTag(String hashTagName) {
        List<Post> posts = hashTagRepository.findPostByHashTag(hashTagName);

        return posts.stream()
                .map(post -> new PostResponseDto(post))
                .collect(Collectors.toList());
    }
}
