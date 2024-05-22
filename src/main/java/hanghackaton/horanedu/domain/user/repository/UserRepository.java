package hanghackaton.horanedu.domain.user.repository;

import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserInquiry{
    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    User findByKakaoId(Long id);

    User findUserByUserDetail(UserDetail userDetail);
}
