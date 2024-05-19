package hanghackaton.horanedu.domain.board.repository.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.entity.QPost;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class PostInquiryImpl extends QuerydslRepositorySupport implements PostInquiry {

    @PersistenceContext
    private EntityManager entityManager;

    public PostInquiryImpl() {
        super(Post.class);
    }

    @Override
    public void increaseViews(Long id) {
        QPost post = QPost.post;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        queryFactory.update(post)
                .set(post.views, post.views.add(1))
                .where(post.id.eq(id))
                .execute();
    }

    @Override
    public Page<PostResponseDto> searchPosts(Pageable pageable, PostCategoryEnum category) {
        QPost post = QPost.post;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Post> result = queryFactory
                .select(post)
                .from(post)
                .where(post.category.eq(category))
                .orderBy(post.created.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(post.category.eq(category));

        List<PostResponseDto> content = result.stream()
                .map(PostResponseDto::new)
                .toList();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}

