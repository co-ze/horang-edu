package hanghackaton.horanedu.school.repository;

import hanghackaton.horanedu.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
    School findSchoolByName(String name);
}
