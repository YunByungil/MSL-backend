package com.maswilaeng.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @Column(name = "tag_name")
    private String tagName;

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
    }

}
