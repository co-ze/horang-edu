package hanghackaton.horanedu.domain.school.repository;

import hanghackaton.horanedu.domain.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>, SchoolInquiry {
    Optional<School> findSchoolByName(String name);

    School findSchoolByGroupCode(String groupCode);
    School findSchoolById(Long id);
}
