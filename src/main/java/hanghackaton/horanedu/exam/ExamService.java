package hanghackaton.horanedu.exam;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.school.repository.SchoolRepository;
import hanghackaton.horanedu.user.entity.Student;
import hanghackaton.horanedu.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;

    @Transactional
    public ResponseDto<String> examSolve() {
        Student student = studentRepository.findStudentByName("cozy");
        School school = schoolRepository.findSchoolById(student.getSchool().getId());
        int level = student.getLevel();
        int exp = student.getExp() + 20;
        if ((student.getExp() % 100) >= 80) {
            level++;
        }
        int chapter = student.getChapter();
        int stage = student.getStage() + 1;
        if (stage == 6) {
            stage = 1;
            chapter += 1;
        }

        student.examReward(exp, chapter, stage, level);
        studentRepository.saveAndFlush(student);

        School userSchool = student.getSchool();
        userSchool.updateScore(20);
        schoolRepository.saveAndFlush(userSchool);

        List<Student> students = studentRepository.findAll();
        updateRank(students);
        List<Student> schoolStudents = studentRepository.findAllBySchool(school);
        updateSchoolRank(schoolStudents);
        List<School> schools = schoolRepository.findAll();
        updateSchoolTotalRank(schools);

        return ResponseDto.setSuccess("exp + 20");
    }

    @Transactional
    public ResponseDto<String> examReset() {
        Student student = studentRepository.findStudentByName("cozy");
        School school = schoolRepository.findSchoolById(student.getSchool().getId());
        int exp = student.getExp();
        student.resetButton();
        studentRepository.saveAndFlush(student);

        school.resetScore(exp);
        schoolRepository.saveAndFlush(school);

        List<Student> students = studentRepository.findAll();
        updateRank(students);
        List<Student> schoolStudents = studentRepository.findAllBySchool(school);
        updateSchoolRank(schoolStudents);
        List<School> schools = schoolRepository.findAll();
        updateSchoolTotalRank(schools);

        return ResponseDto.setSuccess("reset cozy");
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

    @Transactional
    public void updateSchoolTotalRank(List<School> schools) {
        schools.sort(Comparator.comparing(School::getScore).reversed());
        for (int i = 0; i < schools.size(); i++) {
            School school = schools.get(i);
            school.updateRank(i + 1);
            schoolRepository.save(school);
        }
    }

}
