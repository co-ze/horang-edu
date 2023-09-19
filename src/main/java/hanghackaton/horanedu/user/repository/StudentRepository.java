package hanghackaton.horanedu.user.repository;

import hanghackaton.horanedu.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentById(Long id);
}
