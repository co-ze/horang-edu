package hanghackaton.horanedu.user.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.user.dto.OneStudentRankDto;
import hanghackaton.horanedu.user.dto.StudentRankDto;
import hanghackaton.horanedu.user.dto.StudentRequestDto;
import hanghackaton.horanedu.user.dto.StudentResponseDto;
import hanghackaton.horanedu.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseDto<String> createStudent(@RequestBody StudentRequestDto studentRequestDto) {
        return studentService.createUser(studentRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<StudentResponseDto> getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/rank/{id}")
    public ResponseDto<OneStudentRankDto> getStudentRank(@PathVariable Long id) {
        return studentService.getStudentRank(id);
    }
}
