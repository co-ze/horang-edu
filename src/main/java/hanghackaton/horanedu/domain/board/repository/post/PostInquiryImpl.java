package hanghackaton.horanedu.domain.board.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.entity.QPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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
}
