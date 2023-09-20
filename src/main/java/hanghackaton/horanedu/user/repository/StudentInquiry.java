package hanghackaton.horanedu.user.repository;

import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.user.dto.StudentRankDto;
import hanghackaton.horanedu.user.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentInquiry {
    Page<StudentRankDto> studentRank(Pageable pageable);

    Page<StudentRankDto> studentRankInSchool(Pageable pageable, School school);

    List<Student> findAllBySchool(School school);
}
