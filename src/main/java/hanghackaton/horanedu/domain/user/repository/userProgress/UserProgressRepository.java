package hanghackaton.horanedu.domain.user.repository.userProgress;

import hanghackaton.horanedu.domain.user.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
}
