package hanghackaton.horanedu.domain.school.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
import hanghackaton.horanedu.school.entity.QSchool;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class SchoolInquiryImpl extends QuerydslRepositorySupport implements SchoolInquiry{
    @PersistenceContext
    private EntityManager entityManager;

    public SchoolInquiryImpl() {
        super(School.class);
    }

    @Override
    public Page<SchoolRankDto> schoolRank(Pageable pageable) {
        QSchool school = QSchool.school;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<School> result = queryFactory
                .select(school)
                .from(school)
                .orderBy(school.score.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(school.count())
                .from(school);

        List<SchoolRankDto> content = result.stream()
                .map(SchoolRankDto::new)
                .toList();
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
