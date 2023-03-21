package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.domain.repository.HashTagRepository;
import Maswillaeng.MSLback.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HashTagService {

    private final HashTagRepository hashTagRepository;
    private final TagRepository tagRepository;

    public void addHashTag(Post post, List<String> tags) {
        for (String tag : tags) {
            Tag t = findTags(tag);
            hashTagRepository.save(new HashTag(t, post));
        }
    }

    public void updateHashTag(Post post, List<String> tags) {
//        for (String tag : tags) {
//            Tag t = findTags(tag);
//            hashTagRepository.save(new HashTag(t, post));
//        }

        List<HashTag> hashTag = post.getHashTag();
        for (HashTag hashtag : hashTag) {
            hashtag.getTag().getId();
            hashTagRepository.delete(hashtag);
        }
    }

    private Tag findTags(String tag) {
        return tagRepository.findByName(tag)
                .orElseGet(() -> tagRepository.save(new Tag(tag)));

    }
    
    private void deleteTags(Long tagId) {
        tagRepository.findById(tagId);
    }
}
