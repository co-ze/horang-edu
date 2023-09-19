package hanghackaton.horanedu.exam;

import hanghackaton.horanedu.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/api/exam")
    public ResponseDto<String> examSolve() {
        return examService.examSolve();
    }

}
