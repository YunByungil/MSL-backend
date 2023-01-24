package Maswillaeng.MSLback.domain.repository.post;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.QPost;
import Maswillaeng.MSLback.domain.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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
    public List<Post> postList(int page) {
        List<Post> list = jpaQueryFactory
                .selectFrom(post)
                .join(post.user,user).fetchJoin()
                .orderBy(post.createdAt.desc())
                .offset(page*10-10)
                .limit(10)
                .fetch();
        return list;
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
