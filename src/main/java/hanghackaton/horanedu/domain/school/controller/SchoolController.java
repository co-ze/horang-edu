//package hanghackaton.horanedu.domain.school.controller;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.school.dto.SchoolRequestDto;
//import hanghackaton.horanedu.domain.school.service.SchoolService;
//import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/school")
////public class SchoolController {
//
//    private final SchoolService schoolService;
//
//    @PostMapping
//    public ResponseDto<String> createSchool(@RequestBody SchoolRequestDto schoolRequestDto) {
//        return schoolService.createSchool(schoolRequestDto);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseDto<SchoolRankDto> getSchoolRank(@PathVariable Long id) {
//        return schoolService.getSchoolRank(id);
//    }
//}
