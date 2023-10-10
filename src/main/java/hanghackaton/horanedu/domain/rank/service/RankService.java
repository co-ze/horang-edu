//package hanghackaton.horanedu.domain.rank.service;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.school.entity.School;
//import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
//import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
//import hanghackaton.horanedu.domain.user.dto.UserRankDto;
//import hanghackaton.horanedu.domain.user.dto.UserRankInSchoolDto;
//import hanghackaton.horanedu.domain.user.repository.UserDetailRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class RankService {
//    private final UserDetailRepository userDetailRepository;
//    private final SchoolRepository schoolRepository;
//
//    @Transactional(readOnly = true)
//    public ResponseDto<Page<UserRankDto>> getPersonalRankList(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<UserRankDto> personalRankPage = userDetailRepository.userRank(pageable);
//
//        return ResponseDto.setSuccess("개인 순위", personalRankPage);
//    }
//
//    @Transactional(readOnly = true)
//    public ResponseDto<Page<SchoolRankDto>> getSchoolRankList(int page, int size) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "score");
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<SchoolRankDto> schoolRankPage = schoolRepository.schoolRank(pageable);
//
//        return ResponseDto.setSuccess("학교 순위", schoolRankPage);
//    }
//
//    @Transactional(readOnly = true)
//    public ResponseDto<Page<UserRankInSchoolDto>> getPersonalRankInSchoolList(String school, int page, int size) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "exp");
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        School schoolNow = schoolRepository.findSchoolByName(school);
//        Page<UserRankInSchoolDto> studentRankInSchoolDtoPage = userDetailRepository.userRankInSchool(pageable, schoolNow);
//
//        return ResponseDto.setSuccess("학교 내 순위", studentRankInSchoolDtoPage);
//    }
//}
