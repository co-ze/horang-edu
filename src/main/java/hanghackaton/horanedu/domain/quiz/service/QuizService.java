package hanghackaton.horanedu.domain.quiz.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ExceptionEnum;
import hanghackaton.horanedu.common.exception.GlobalException;
import hanghackaton.horanedu.domain.quiz.dto.QuizRequestDto;
import hanghackaton.horanedu.domain.quiz.dto.QuizResponseDto;
import hanghackaton.horanedu.domain.quiz.entity.Quiz;
import hanghackaton.horanedu.domain.quiz.repository.QuizRepository;
import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.school.repository.SchoolRepository;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserProgress;
import hanghackaton.horanedu.domain.user.repository.UserRepository;
import hanghackaton.horanedu.domain.user.repository.userProgress.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;
    private final SchoolRepository schoolRepository;

    @Transactional
    public ResponseDto<QuizResponseDto> attemptQuiz(User loginUser, QuizRequestDto quizRequestDto) {
        log.info("---------------attemptQuiz START----------------");
        User user = userRepository.findUserById(loginUser.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_USER)
        );
        UserProgress userProgress = userProgressRepository.findUserProgressByUser(user);
        if(userProgress == null) throw new GlobalException(ExceptionEnum.NOT_FOUND_USERPROGRESS);

        Quiz quiz = quizRepository.findById(quizRequestDto.getId()).orElseThrow(
                () -> new GlobalException(ExceptionEnum.NOT_FOUND_QUIZ)
        );



        if(Objects.equals(quiz.getAnswer(),quizRequestDto.getAnswer())){
            userProgress.setChapter(quiz.getChapter());
            userProgress.setStage(quiz.getStage());
            userProgress.setLevel(userProgress.getExp()+ quiz.getExp());
            userProgress.setExp(quiz.getExp());
            userProgressRepository.saveAndFlush(userProgress);
            QuizResponseDto response = new QuizResponseDto(quiz);

            if(user.getUserDetail().getSchool() != null){
                School group = schoolRepository.findSchoolById(user.getUserDetail().getSchool().getId());
                group.updateScore(quiz.getExp());
                schoolRepository.saveAndFlush(group);
            }
            return ResponseDto.set(HttpStatus.OK, "정답!", response);
        }else{
            QuizResponseDto response = new QuizResponseDto(quiz);
            return ResponseDto.set(HttpStatus.OK, "오답!", response);
        }
    }
}
