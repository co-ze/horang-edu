package hanghackaton.horanedu.domain.user.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.dto.StudentRequestDto;
import hanghackaton.horanedu.domain.user.dto.StudentResponseDto;
import hanghackaton.horanedu.domain.user.entity.Student;
import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
import hanghackaton.horanedu.domain.user.dto.OneStudentRankDto;
import hanghackaton.horanedu.domain.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;

    @Transactional
    public ResponseDto<String> createUser(StudentRequestDto studentRequestDto) {

        School school = schoolRepository.findSchoolByName(studentRequestDto.getSchool());
        Student student = new Student(studentRequestDto, school);
        studentRepository.saveAndFlush(student);

        school.getStudentList().add(student);
        school.updateScore(student.getExp());

        List<Student> students = studentRepository.findAll();
        updateRank(students);
        List<Student> schoolStudents = studentRepository.findAllBySchool(school);
        updateSchoolRank(schoolStudents);

        return ResponseDto.setSuccess("아이디 생성");

    }

    @Transactional(readOnly = true)
    public ResponseDto<StudentResponseDto> getStudent(Long id) {
        Student student = studentRepository.findStudentById(id);
        StudentResponseDto studentResponseDto = new StudentResponseDto(student);
        return ResponseDto.setSuccess("학생 정보", studentResponseDto);
    }

    @Transactional(readOnly = true)
    public ResponseDto<OneStudentRankDto> getStudentRank(Long id) {
        Student student = studentRepository.findStudentById(id);
        OneStudentRankDto studentRankDto = new OneStudentRankDto(student);
        return ResponseDto.setSuccess("내 순위 정보", studentRankDto);
    }

    @Transactional
    public void updateRank(List<Student> students) {
        students.sort(Comparator.comparing(Student::getExp).reversed());
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            student.updateRank(i + 1);
            studentRepository.save(student);
        }
    }

    @Transactional
    public void updateSchoolRank(List<Student> schoolStudents) {
        schoolStudents.sort(Comparator.comparing(Student::getExp).reversed());
        for (int i = 0; i < schoolStudents.size(); i++) {
            Student student = schoolStudents.get(i);
            student.updateSchoolRank(i + 1);
            studentRepository.save(student);
        }
    }


}
