package hanghackaton.horanedu.school.repository;

import hanghackaton.horanedu.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>, SchoolInquiry {
    School findSchoolByName(String name);

    School findSchoolById(Long id);
}
