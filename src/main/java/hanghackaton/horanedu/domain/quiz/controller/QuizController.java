package hanghackaton.horanedu.domain.quiz.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.quiz.dto.QuizRequestDto;
import hanghackaton.horanedu.domain.quiz.dto.QuizResponseDto;
import hanghackaton.horanedu.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;

    @PostMapping()
    public ResponseDto<QuizResponseDto> attemptQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @RequestBody QuizRequestDto quizRequestDto){
        return quizService.attemptQuiz(userDetails.getUser(), quizRequestDto);
    }
}
