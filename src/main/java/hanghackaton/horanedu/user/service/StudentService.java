package hanghackaton.horanedu.user.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.school.repository.SchoolRepository;
import hanghackaton.horanedu.user.dto.StudentRequestDto;
import hanghackaton.horanedu.user.dto.StudentResponseDto;
import hanghackaton.horanedu.user.entity.Student;
import hanghackaton.horanedu.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ResponseDto.setSuccess("아이디 생성");
    }

    @Transactional(readOnly = true)
    public ResponseDto<StudentResponseDto> getStudent(Long id) {
        Student student = studentRepository.findStudentById(id);
        StudentResponseDto studentResponseDto = new StudentResponseDto(student);
        return ResponseDto.setSuccess("학생 정보", studentResponseDto);
    }
}
