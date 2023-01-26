package Maswillaeng.MSLback.domain.repository.post;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.QPost;
import Maswillaeng.MSLback.domain.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

    public PostRepositoryCustomImpl() {
        super(Post.class);
    }

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    static QPost post = QPost.post;
    static QUser user = QUser.user;


    @Override
    public Page<Post> postList(Pageable pageable) {
        List<Post> list = jpaQueryFactory
                .selectFrom(post)
                .join(post.user,user).fetchJoin()
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

         Long count = jpaQueryFactory
                 .select(post.count())
                 .from(post)
                 .fetchOne();
        return PageableExecutionUtils.getPage(list,pageable,()->count);
    }



    @Override
    public Post getPost(Long postId) {
        return jpaQueryFactory
                .selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(post.postId.eq(postId))
                .fetchOne();
    }


}
