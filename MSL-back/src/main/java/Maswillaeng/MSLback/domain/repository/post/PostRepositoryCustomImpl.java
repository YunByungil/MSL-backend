package Maswillaeng.MSLback.domain.repository.post;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.QPost;
import Maswillaeng.MSLback.domain.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

//public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public PostRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
//        super(Post.class);
//        this.jpaQueryFactory = jpaQueryFactory;
//    }
//
//
//    static QPost post = QPost.post;
//    static QUser user = QUser.user;


//    @Override
//    public Page<Post> postList(Pageable pageable) {
//        List<Post> list = jpaQueryFactory
//                .selectFrom(post)
//                .join(post.user,user).fetchJoin()
//                .orderBy(post.createdAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        return list;
//
//    }

//
//    @Override
//    public Post getPost(Long postId) {
//        return jpaQueryFactory
//                .selectFrom(post)
//                .join(post.user, user).fetchJoin()
//                .where(post.postId.eq(postId))
//                .fetchOne();
//    }

//    @Override
//    public Page<Post> userPostList(Long userId,Pageable pageable) {
//        List<Post> userPostList = jpaQueryFactory
//                .selectFrom(post)
//                .join(post.user, user).fetchJoin()
//                .where(post.user.user_id.eq(userId))
//                .orderBy(post.createdAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Long count = jpaQueryFactory
//                .select(post.count())
//                .from(post)
//                .where(post.user.user_id.eq(userId))
//                .fetchOne();
//
//        return PageableExecutionUtils.getPage(userPostList,pageable,()->count);
//    }


//}
