package Maswillaeng.MSLback.domain.repository.post.query;

import Maswillaeng.MSLback.dto.post.reponse.PostListResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostSearchCondition;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static Maswillaeng.MSLback.domain.entity.QPost.*;
import static Maswillaeng.MSLback.domain.entity.QUser.user;
import static org.springframework.util.StringUtils.*;

@Repository
public class PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    public PostSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<PostListResponseDto> search(PostSearchCondition condition, Pageable pageable) {
        List<PostListResponseDto> content = queryFactory
                .select(Projections.constructor(PostListResponseDto.class,
                        post.id,
                        user.id,
                        user.nickname,
                        post.thumbnail,
                        post.title,
                        post.content,
                        post.hits,
                        post.category,
                        post.comment.size(),
                        post.postLike.size(),
                        post.createAt))
                .from(post)
                .leftJoin(post.user, user)
                .where(
                        postWriterEq(condition.getPostWriter()),
                        titleEq(condition.getTitle()),
                        postContentEq(condition.getPostContent())
//                        commentWriter(condition.getCommentWriter()),
//                        commentContent(condition.getCommentContent())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .leftJoin(post.user, user)
                .where(
                        postWriterEq(condition.getPostWriter()),
                        titleEq(condition.getTitle()),
                        postContentEq(condition.getPostContent())
//                        commentWriter(condition.getCommentWriter()),
//                        commentContent(condition.getCommentContent())
                );


        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

    private BooleanExpression postWriterEq(String postWriter) {
        if (!hasText(postWriter)) {
            return null;
        }
        return user.nickname.eq(postWriter);
    }

    private BooleanExpression titleEq(String title) {
        if (!hasText(title)) {
            return null;
        }
        return post.title.eq(title);
    }

    private BooleanExpression postContentEq(String postContent) {
        if (!hasText(postContent)) {
            return null;
        }
        return post.content.eq(postContent);
    }

//    private BooleanExpression commentWriter(String commentWriter) {
//        if (!hasText(commentWriter)) {
//            return null;
//        }
//        return post.
//    }


}
