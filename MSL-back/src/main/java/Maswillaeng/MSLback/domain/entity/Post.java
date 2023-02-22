package Maswillaeng.MSLback.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table( name = "post")
@Getter @Setter
public class Post {


    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false) //혹시 모를 user_id 수정이나 삭제에 대비하여 rock
    private User user;

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "POST_ID")
    private Long post_id;

    private Long user_id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;

    private String thumbnail;

    private String title;

    private Long hits = 0L;

    private int report;

    @CreationTimestamp
    private Date modified_at;

    @CreationTimestamp
    private Date created_at;





}
