package hanghackaton.horanedu.school.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.dto.SchoolRankDto;
import hanghackaton.horanedu.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional
    public ResponseDto<String> createSchool(SchoolRequestDto schoolRequestDto) {
        School school = new School(schoolRequestDto);
        schoolRepository.saveAndFlush(school);
        List<School> schools = schoolRepository.findAll();
        updateRank(schools);
        return ResponseDto.setSuccess("학교 생성");
    }

    @Transactional
    public void updateRank(List<School> schools) {
        schools.sort(Comparator.comparing(School::getScore).reversed());
        for (int i = 0; i < schools.size(); i++) {
            School school = schools.get(i);
            school.updateRank(i + 1);
            schoolRepository.save(school);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<SchoolRankDto> getSchoolRank(Long id) {
        School school = schoolRepository.findSchoolById(id);
        SchoolRankDto schoolRankDto = new SchoolRankDto(school);
        return ResponseDto.setSuccess("내 학교 순위 정보", schoolRankDto);
    }
}
