package hanghackaton.horanedu.domain.quiz.dto;

import hanghackaton.horanedu.domain.quiz.entity.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizResponseDto {
    private Long id;
    private Boolean answer;
    private int chapter;
    private int stage;
    private int exp;

    public QuizResponseDto(Quiz quiz) {
        this.id = quiz.getId();
        this.answer = quiz.getAnswer();
        this.chapter = quiz.getChapter();
        this.stage = quiz.getStage();
        this.exp = quiz.getExp();
    }
}
