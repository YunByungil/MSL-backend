package com.maswilaeng.domain.repository;

import com.maswilaeng.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByTagName(String tagName);

    void deleteByTagName(String tagName);
}
