package hanghackaton.horanedu.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghackaton.horanedu.domain.user.entity.QUser;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

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
}
