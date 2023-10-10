//package hanghackaton.horanedu.domain.user.repository;
//
//import hanghackaton.horanedu.domain.school.entity.School;
//import hanghackaton.horanedu.domain.user.dto.UserRankInSchoolDto;
//import hanghackaton.horanedu.domain.user.entity.UserDetail;
//import hanghackaton.horanedu.domain.user.dto.UserRankDto;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//public interface UserDetailInquiry {
//    Page<UserRankDto> userRank(Pageable pageable);
//
//    Page<UserRankInSchoolDto> userRankInSchool(Pageable pageable, School school);
//
//    List<UserDetail> findAllBySchool(School school);
//}
