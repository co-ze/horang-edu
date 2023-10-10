//package hanghackaton.horanedu.domain.exam;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.school.entity.School;
//import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
//import hanghackaton.horanedu.domain.user.entity.UserDetail;
//import hanghackaton.horanedu.domain.user.repository.UserDetailRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Comparator;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ExamService {
//
//    private final UserDetailRepository userDetailRepository;
//    private final SchoolRepository schoolRepository;
//
//    @Transactional
//    public ResponseDto<String> examSolve() {
//        UserDetail user = userDetailRepository.findUserByName("cozy");
//        School school = schoolRepository.findSchoolById(user.getSchool().getId());
//        int level = user.getLevel();
//        int exp = user.getExp() + 20;
//        if ((user.getExp() % 100) >= 80) {
//            level++;
//        }
//        int chapter = user.getChapter();
//        int stage = user.getStage() + 1;
//        if (stage == 6) {
//            stage = 1;
//            chapter += 1;
//        }
//
//        user.examReward(exp, chapter, stage, level);
//        userDetailRepository.saveAndFlush(user);
//
//        School userSchool = user.getSchool();
//        userSchool.updateScore(20);
//        schoolRepository.saveAndFlush(userSchool);
//
//        List<UserDetail> users = userDetailRepository.findAll();
//        updateRank(users);
//        List<UserDetail> schoolUsers = userDetailRepository.findAllBySchool(school);
//        updateSchoolRank(schoolUsers);
//        List<School> schools = schoolRepository.findAll();
//        updateSchoolTotalRank(schools);
//
//        return ResponseDto.setSuccess("exp + 20");
//    }
//
//    @Transactional
//    public ResponseDto<String> examReset() {
//        UserDetail user = userDetailRepository.findUserByName("cozy");
//        School school = schoolRepository.findSchoolById(user.getSchool().getId());
//        int exp = user.getExp();
//        user.resetButton();
//        userDetailRepository.saveAndFlush(user);
//
//        school.resetScore(exp);
//        schoolRepository.saveAndFlush(school);
//
//        List<UserDetail> users = userDetailRepository.findAll();
//        updateRank(users);
//        List<UserDetail> schoolUsers = userDetailRepository.findAllBySchool(school);
//        updateSchoolRank(schoolUsers);
//        List<School> schools = schoolRepository.findAll();
//        updateSchoolTotalRank(schools);
//
//        return ResponseDto.setSuccess("reset cozy");
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
//
//}
