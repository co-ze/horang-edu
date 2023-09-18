package hanghackaton.horanedu.exam;

import hanghackaton.horanedu.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/api/exam")
    public ResponseDto<String> examSolve() {
        return examService.examSolve();
    }

}
