package hanghackaton.horanedu.domain.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizRequestDto {
    private Long id;
    private Boolean answer;
}
