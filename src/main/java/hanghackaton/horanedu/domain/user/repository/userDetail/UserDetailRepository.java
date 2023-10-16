package hanghackaton.horanedu.domain.user.repository.userDetail;

import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findUserDetailByUser(User user);
}
