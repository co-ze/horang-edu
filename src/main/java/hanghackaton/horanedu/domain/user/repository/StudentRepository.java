package hanghackaton.horanedu.domain.user.repository;

import hanghackaton.horanedu.domain.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, StudentInquiry{
    Student findStudentById(Long id);

    Student findStudentByName(String name);
}
