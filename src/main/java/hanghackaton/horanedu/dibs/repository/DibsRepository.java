package hanghackaton.horanedu.dibs.repository;

import hanghackaton.horanedu.dibs.entity.Dibs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DibsRepository extends JpaRepository<Dibs, Long> {
    Dibs findDibsById(Long id);
}
