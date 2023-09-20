package hanghackaton.horanedu.domain.homework.repository;

import hanghackaton.horanedu.domain.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
