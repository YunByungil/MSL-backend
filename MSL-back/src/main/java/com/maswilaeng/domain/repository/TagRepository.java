package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface TagRepository extends JpaRepository<Tag, Long> {

    void deleteByTagName(String tagName);


    @Query("select t from Tag t where t.tagName in :tagNameSet")
    Set<Tag> findByNameSet(@Param("tagNameSet")Set<String> tagNameSet);


}
