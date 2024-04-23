package hanghackaton.horanedu.domain.school.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.domain.school.service.SchoolService;
import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    public ResponseDto<String> createSchool(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody SchoolRequestDto schoolRequestDto) {
        return schoolService.createSchool(userDetails.getUser(), schoolRequestDto);
    }

    @PatchMapping("/code/{id}")
    public ResponseDto<String> patchGroupCode(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return schoolService.patchGroupCode(id, userDetails.getUser());
    }

//    @GetMapping("/{id}")
//    public ResponseDto<SchoolRankDto> getSchoolRank(@PathVariable Long id) {
//        return schoolService.getSchoolRank(id);
//    }
}
