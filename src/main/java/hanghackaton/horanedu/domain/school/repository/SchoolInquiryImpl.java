package hanghackaton.horanedu.domain.school.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
import hanghackaton.horanedu.domain.school.entity.QSchool;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.dto.UserRankDto;
import hanghackaton.horanedu.domain.user.entity.QUserProgress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class SchoolInquiryImpl extends QuerydslRepositorySupport implements SchoolInquiry{
    @PersistenceContext
    private EntityManager entityManager;

    public SchoolInquiryImpl() {
        super(School.class);
    }

    @Override
    public List<SchoolRankDto> getSchoolRank() {
        QSchool school = QSchool.school;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<School> result = queryFactory
                .select(school)
                .from(school)
                .orderBy(school.score.desc())
                .fetch();

        return IntStream.range(0, result.size())
                .mapToObj(index -> new SchoolRankDto(result.get(index), index+1))
                .toList();
    }

    @Override
    public int getLoginUserSchoolRank(List<SchoolRankDto> ranking, Long id){
        OptionalInt schoolIndex = IntStream.range(0, ranking.size())
                .filter(index -> ranking.get(index).getId().equals(id))
                .findFirst();

        if(schoolIndex.isPresent()){
            return schoolIndex.getAsInt()+1;
        }else{
            return 0;
        }
    }
}
