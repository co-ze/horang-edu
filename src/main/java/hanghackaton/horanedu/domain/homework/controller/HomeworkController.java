//package hanghackaton.horanedu.domain.homework.controller;
//
//import hanghackaton.horanedu.common.dto.ResponseDto;
//import hanghackaton.horanedu.domain.homework.service.HomeworkService;
//import hanghackaton.horanedu.domain.homework.dto.HomeworkRequestDto;
//import hanghackaton.horanedu.domain.homework.dto.HomeworkResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/homework")
//public class HomeworkController {
//    private final HomeworkService homeworkService;
//
//    @PostMapping
//    public ResponseDto<String> createHomework(@RequestBody HomeworkRequestDto homeworkRequestDto) {
//        return homeworkService.createHomework(homeworkRequestDto);
//    }
//
//    @PostMapping("/{id}")
//    public ResponseDto<String> checkHomework(@PathVariable Long id) {
//        return homeworkService.checkHomework(id);
//    }
//
//    @GetMapping
//    public ResponseDto<List<HomeworkResponseDto>> getHomeworkList() {
//        return homeworkService.getHomeworkList();
//    }
//}
