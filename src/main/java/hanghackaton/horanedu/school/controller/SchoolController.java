package hanghackaton.horanedu.school.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping()
    public ResponseDto<String> createSchool(@RequestBody SchoolRequestDto schoolRequestDto) {
        return schoolService.createSchool(schoolRequestDto);
    }
}
