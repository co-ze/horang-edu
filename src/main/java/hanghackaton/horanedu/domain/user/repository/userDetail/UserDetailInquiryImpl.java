//package hanghackaton.horanedu.domain.user.repository;
//
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import hanghackaton.horanedu.domain.school.entity.School;
//import hanghackaton.horanedu.domain.user.dto.UserRankDto;
//import hanghackaton.horanedu.domain.user.dto.UserRankInSchoolDto;
//import hanghackaton.horanedu.domain.user.entity.QStudent;
//import hanghackaton.horanedu.domain.user.entity.UserDetail;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.data.support.PageableExecutionUtils;
//
//import java.util.List;
//
//public class UserDetailInquiryImpl extends QuerydslRepositorySupport implements UserDetailInquiry {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public UserDetailInquiryImpl() {
//        super(UserDetail.class);
//    }
//
//    @Override
//    public Page<UserRankDto> userRank(Pageable pageable) {
//        QStudent student = QStudent.student;
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//
//        List<UserDetail> result = queryFactory
//                .select(student)
//                .from(student)
//                .orderBy(student.exp.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = queryFactory
//                .select(student.count())
//                .from(student);
//
//        List<UserRankDto> content = result.stream()
//                .map(UserRankDto::new)
//                .toList();
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//    }
//
//    @Override
//    public Page<UserRankInSchoolDto> userRankInSchool(Pageable pageable, School school) {
//        QStudent student = QStudent.student;
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//
//        List<UserDetail> result = queryFactory
//                .select(student)
//                .from(student)
//                .where(student.school.eq(school))
//                .orderBy(student.exp.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = queryFactory
//                .select(student.count())
//                .from(student)
//                .where(student.school.eq(school));
//
//        List<UserRankInSchoolDto> content = result.stream()
//                .map(UserRankInSchoolDto::new)
//                .toList();
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//    }
//
//    @Override
//    public List<UserDetail> findAllBySchool(School school) {
//        QStudent student = QStudent.student;
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//
//        return queryFactory
//                .selectFrom(student)
//                .where(student.school.eq(school))
//                .fetch();
//    }
//}
