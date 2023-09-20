package hanghackaton.horanedu.domain.dibs.repository;

import hanghackaton.horanedu.domain.dibs.entity.Dibs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DibsRepository extends JpaRepository<Dibs, Long> {
    Dibs findDibsById(Long id);
}
