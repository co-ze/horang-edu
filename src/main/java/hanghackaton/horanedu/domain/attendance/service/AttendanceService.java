//package hanghackaton.horanedu.domain.attendance.service;
//
//import hanghackaton.horanedu.domain.attendance.entity.Attendance;
//import hanghackaton.horanedu.domain.attendance.repository.AttendanceRepository;
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.school.entity.School;
//import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
//import hanghackaton.horanedu.domain.user.entity.UserDetail;
//import hanghackaton.horanedu.domain.user.repository.UserDetailRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AttendanceService {
//    private final AttendanceRepository attendanceRepository;
//    private final UserDetailRepository userDetailRepository;
//    private final SchoolRepository schoolRepository;
//
//    @Transactional(readOnly = true)
//    public ResponseDto<List<String>> getAttendanceList() {
//        List<Attendance> attendanceList = attendanceRepository.findAll();
//        List<String> dayList = new ArrayList<>();
//
//        for (Attendance attendance : attendanceList) {
//            dayList.add(attendance.getTodayDate());
//        }
//
//        return ResponseDto.setSuccess("출석 일자 리스트", dayList);
//    }
//
//    @Transactional
//    public ResponseDto<String> attendanceCheck(String date) {
//        Attendance attendance = new Attendance(date);
//        attendanceRepository.saveAndFlush(attendance);
//
//        UserDetail user = userDetailRepository.findUserByName("cozy");
//        School school = schoolRepository.findSchoolById(user.getSchool().getId());
//        int level = user.getLevel();
//        int exp = user.getExp() + 1;
//        if ((user.getExp() % 100) >= 99) {
//            level++;
//        }
//        user.attendanceReward(exp, level);
//        userDetailRepository.saveAndFlush(user);
//
//        School userSchool = user.getSchool();
//        userSchool.updateScore(1);
//        schoolRepository.saveAndFlush(userSchool);
//
//        List<UserDetail> users = userDetailRepository.findAll();
//        updateRank(users);
//        List<UserDetail> schoolUsers = userDetailRepository.findAllBySchool(school);
//        updateSchoolRank(schoolUsers);
//        List<School> schools = schoolRepository.findAll();
//        updateSchoolTotalRank(schools);
//
//        return ResponseDto.setSuccess("출석 체크!");
//    }
//
//    @Transactional
//    public void updateRank(List<UserDetail> users) {
//        users.sort(Comparator.comparing(UserDetail::getExp).reversed());
//        for (int i = 0; i < users.size(); i++) {
//            UserDetail user = users.get(i);
//            user.updateRank(i + 1);
//            userDetailRepository.save(user);
//        }
//    }
//
//    @Transactional
//    public void updateSchoolRank(List<UserDetail> schoolUsers) {
//        schoolUsers.sort(Comparator.comparing(UserDetail::getExp).reversed());
//        for (int i = 0; i < schoolUsers.size(); i++) {
//            UserDetail user = schoolUsers.get(i);
//            user.updateSchoolRank(i + 1);
//            userDetailRepository.save(user);
//        }
//    }
//
//    @Transactional
//    public void updateSchoolTotalRank(List<School> schools) {
//        schools.sort(Comparator.comparing(School::getScore).reversed());
//        for (int i = 0; i < schools.size(); i++) {
//            School school = schools.get(i);
//            school.updateRank(i + 1);
//            schoolRepository.save(school);
//        }
//    }
//}
