package hanghackaton.horanedu.attendance.service;

import hanghackaton.horanedu.attendance.entity.Attendance;
import hanghackaton.horanedu.attendance.repository.AttendanceRepository;
import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.school.repository.SchoolRepository;
import hanghackaton.horanedu.user.entity.Student;
import hanghackaton.horanedu.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<String>> getAttendanceList() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        List<String> dayList = new ArrayList<>();

        for (Attendance attendance : attendanceList) {
            dayList.add(attendance.getTodayDate());
        }

        return ResponseDto.setSuccess("출석 일자 리스트", dayList);
    }

    @Transactional
    public ResponseDto<String> attendanceCheck(String date) {
        Attendance attendance = new Attendance(date);
        attendanceRepository.saveAndFlush(attendance);

        Student student = studentRepository.findStudentByName("cozy");
        School school = schoolRepository.findSchoolById(student.getSchool().getId());
        int level = student.getLevel();
        int exp = student.getExp() + 1;
        if ((student.getExp() % 100) >= 99) {
            level++;
        }
        student.attendanceReward(exp, level);
        studentRepository.saveAndFlush(student);

        School userSchool = student.getSchool();
        userSchool.updateScore(1);
        schoolRepository.saveAndFlush(userSchool);

        List<Student> students = studentRepository.findAll();
        updateRank(students);
        List<Student> schoolStudents = studentRepository.findAllBySchool(school);
        updateSchoolRank(schoolStudents);
        List<School> schools = schoolRepository.findAll();
        updateSchoolTotalRank(schools);

        return ResponseDto.setSuccess("출석 체크!");
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
