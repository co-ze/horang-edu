package hanghackaton.horanedu.domain.user.repository;

import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.entity.Student;
import hanghackaton.horanedu.domain.user.dto.StudentRankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentInquiry {
    Page<StudentRankDto> studentRank(Pageable pageable);

    Page<StudentRankDto> studentRankInSchool(Pageable pageable, School school);

    List<Student> findAllBySchool(School school);
}
