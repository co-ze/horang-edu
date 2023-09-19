package hanghackaton.horanedu.school.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public ResponseDto<String> createSchool(SchoolRequestDto schoolRequestDto) {
        School school = new School(schoolRequestDto);
        schoolRepository.save(school);
        return ResponseDto.setSuccess("학교 생성");
    }
}
