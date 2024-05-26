package hanghackaton.horanedu.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.user.dto.UserRankDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.UserRankResponseDto;
import hanghackaton.horanedu.domain.user.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserInquiryImpl extends QuerydslRepositorySupport implements UserInquiry {

    @PersistenceContext
    private EntityManager entityManager;

    public UserInquiryImpl() {
        super(UserDetail.class);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        QUser user = QUser.user;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<User> result = queryFactory
                .select(user)
                .from(user)
                .where(user.id.eq(id))
                .fetch();

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public List<UserRankDto> getUserRank(){
        QUserProgress userProgress = QUserProgress.userProgress;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<UserProgress> result = queryFactory
                .selectFrom(userProgress)
                .orderBy(userProgress.exp.desc())
                .fetch();

        return IntStream.range(0, result.size())
                .mapToObj(index -> new UserRankDto(result.get(index), index+1))
                .toList();
    }

    @Override
    public int getLoginUserRank(List<UserRankDto> ranking, Long id){
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
