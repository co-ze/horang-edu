package hanghackaton.horanedu.domain.board.repository.classPost;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.board.dto.ClassPostResponseDto;
import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.entity.QClassPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class ClassPostInquiryImpl extends QuerydslRepositorySupport implements ClassPostInquiry {
    @PersistenceContext
    private EntityManager entityManager;

    public ClassPostInquiryImpl() {
        super(Post.class);
    }

    @Override
    public Page<ClassPostResponseDto> searchClassPosts(Pageable pageable, String grade) {
        QClassPost classPost = QClassPost.classPost;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<ClassPost> result = queryFactory
                .select(classPost)
                .from(classPost)
                .where(classPost.gradeClass.eq(grade))
                .orderBy(classPost.created.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(classPost.count())
                .from(classPost)
                .where(classPost.gradeClass.eq(grade));

        List<ClassPostResponseDto> content = result.stream()
                .map(ClassPostResponseDto::new)
                .toList();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
