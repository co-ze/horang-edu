package hanghackaton.horanedu.domain.user.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.entity.Student;
import hanghackaton.horanedu.domain.user.dto.StudentRankDto;
import hanghackaton.horanedu.user.entity.QStudent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class StudentInquiryImpl extends QuerydslRepositorySupport implements StudentInquiry{
    @PersistenceContext
    private EntityManager entityManager;

    public StudentInquiryImpl() {
        super(Student.class);
    }

    @Override
    public Page<StudentRankDto> studentRank(Pageable pageable) {
        QStudent student = QStudent.student;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Student> result = queryFactory
                .select(student)
                .from(student)
                .orderBy(student.exp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(student.count())
                .from(student);

        List<StudentRankDto> content = result.stream()
                .map(StudentRankDto::new)
                .toList();
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<StudentRankDto> studentRankInSchool(Pageable pageable, School school) {
        QStudent student = QStudent.student;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Student> result = queryFactory
                .select(student)
                .from(student)
                .where(student.school.eq(school))
                .orderBy(student.exp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(student.count())
                .from(student)
                .where(student.school.eq(school));

        List<StudentRankDto> content = result.stream()
                .map(StudentRankDto::new)
                .toList();
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Student> findAllBySchool(School school) {
        QStudent student = QStudent.student;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory
                .selectFrom(student)
                .where(student.school.eq(school))
                .fetch();
    }
}
