package hanghackaton.horanedu.domain.school.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<String> createSchool(User loginUser, SchoolRequestDto schoolRequestDto) {

        User teacher = userRepository.findUserById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

        School school = new School(schoolRequestDto, teacher.getId());
        schoolRepository.save(school);

        return ResponseDto.setSuccess(HttpStatus.OK, "학교가 생성되었습니다.");
    }

    @Transactional
    public ResponseDto<String> patchGroupCode(Long schoolId, User loginUser) {

        User teacher = userRepository.findUserById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );

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

//    @Transactional
//    public void updateRank(List<School> schools) {
//
//    }

//    @Transactional(readOnly = true)
//    public ResponseDto<SchoolRankDto> getSchoolRank(Long id) {
//
//    }
}
