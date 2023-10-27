package hanghackaton.horanedu.domain.user.repository;

import hanghackaton.horanedu.domain.user.entity.User;

import java.util.Optional;

public interface UserInquiry {
    Optional<User> findUserById(Long id);
}
