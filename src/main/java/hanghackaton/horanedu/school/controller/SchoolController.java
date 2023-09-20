package hanghackaton.horanedu.school.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.dto.SchoolRankDto;
import hanghackaton.horanedu.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    public ResponseDto<String> createSchool(@RequestBody SchoolRequestDto schoolRequestDto) {
        return schoolService.createSchool(schoolRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<SchoolRankDto> getSchoolRank(@PathVariable Long id) {
        return schoolService.getSchoolRank(id);
    }
}
