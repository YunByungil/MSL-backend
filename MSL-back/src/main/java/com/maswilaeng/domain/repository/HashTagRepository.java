package com.maswilaeng.domain.repository;


import com.maswilaeng.domain.entity.HashTag;
import com.maswilaeng.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    @Modifying
    @Query("delete from HashTag h where h.post.id =:postId")
    void deleteByPostId(@Param("postId") Long postId);

    @Query("select h from HashTag h where h.tag.tagName =:tagName")
    Set<HashTag> findByTagName(@Param("tagName") String TagName);

    Set<HashTag> findByPost(Post post);

    @Query("select distinct p from Post p join fetch p.hashTagSet h where h.tag.tagName =:hashTag")
    List<Post> findPostByHashTag(@Param("hashTag")String hashTag);
}
