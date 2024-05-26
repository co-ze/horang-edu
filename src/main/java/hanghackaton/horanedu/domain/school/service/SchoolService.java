package hanghackaton.horanedu.domain.school.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.school.dto.*;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.user.repository.userDetail.UserDetailRepository;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @Transactional
    public ResponseDto<String> createSchool(User loginUser, SchoolRequestDto schoolRequestDto) {

        User teacher = userRepository.findUserById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        if(teacher.getRole() != UserRole.TEACHER) throw new GlobalException(ExceptionEnum.UNAUTHORIZED);

        School school = new School(schoolRequestDto, teacher.getId());
        schoolRepository.saveAndFlush(school);

        UserDetail tDetail = userDetailRepository.findUserDetailByUser(teacher);
        if(tDetail == null) throw new GlobalException(ExceptionEnum.NOT_FOUND_USER);

        tDetail.setSchool(school);
        userDetailRepository.save(tDetail);

        return ResponseDto.setSuccess(HttpStatus.OK, "학교가 생성되었습니다.");
    }

    @Transactional
    public ResponseDto<String> patchGroupCode(Long schoolId, User loginUser) {

        User teacher = userRepository.findUserById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        if(teacher.getRole() != UserRole.TEACHER) throw new GlobalException(ExceptionEnum.UNAUTHORIZED);

        School school = schoolRepository.findById(schoolId).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_SCHOOL)
        );

        if(Objects.equals(school.getTeacher(), teacher.getId())) {
            StringBuffer randCode = new StringBuffer();
            char[] str = new char[1];
            for (int i = 0; i < 6; i++) {
                str[0] = (char) ((Math.random() * 26) + 65);
                randCode.append(str);
            }
            school.setGroupCode(randCode);
            schoolRepository.saveAndFlush(school);

            return ResponseDto.setSuccess(HttpStatus.OK, "그룹 코드 생성 완료.", school.getGroupCode());
        }else {
            throw new GlobalException(ExceptionEnum.UNAUTHORIZED);
        }
    }

    @Transactional
    public ResponseDto<SchoolResponseDto> getSchool(User user) {
        School group = user.getUserDetail().getSchool();
        if(group == null) throw new GlobalException(ExceptionEnum.NOT_FOUND_SCHOOL);

        List<UserListDto> userListDto = new ArrayList<>();
        List<UserDetail> userDetailList = group.getUserDetailList();
        int size = userDetailList.size();

        for(int i = 0; i < size; i++){
            User groupUser = userRepository.findUserByUserDetail(userDetailList.get(i));
            UserListDto userDto = new UserListDto(groupUser);
            userListDto.add(userDto);
        }

        SchoolResponseDto response = new SchoolResponseDto(group, user.getName(), userListDto);

        return ResponseDto.set(HttpStatus.OK, "그룹 조회", response);
    }

    @Transactional(readOnly = true)
    public ResponseDto<SchoolRankResponseDto> getSchoolRank(User loginUser){
        Optional<School> userGroup = schoolRepository.searchSchoolById(loginUser.getUserDetail().getSchool().getId());

        if(userGroup.isPresent()) {
            String groupName = userGroup.get().getName();
            int groupScore = userGroup.get().getScore();
            List<SchoolRankDto> ranking = schoolRepository.getSchoolRank();
            int groupRank = schoolRepository.getLoginUserSchoolRank(ranking, userGroup.get().getId());
            SchoolRankResponseDto response = new SchoolRankResponseDto(groupName, groupRank, groupScore, ranking);
            return ResponseDto.set(HttpStatus.OK, "학교 랭킹 조회", response);
        }else{
            List<SchoolRankDto> ranking = schoolRepository.getSchoolRank();
            SchoolRankResponseDto response = new SchoolRankResponseDto("", 0, 0, ranking);
            return ResponseDto.set(HttpStatus.OK, "학교 랭킹 조회", response);
        }
    }
}
