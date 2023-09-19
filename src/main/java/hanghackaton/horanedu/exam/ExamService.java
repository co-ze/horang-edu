package hanghackaton.horanedu.exam;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.user.entity.Student;
import hanghackaton.horanedu.user.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final StudentRepository studentRepository;

    @Transactional
    public ResponseDto<String> examSolve() {
        Student student = studentRepository.findById(1L).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        int score = 50;
        String stage = "2-5";
        int level = student.getLevel();
        if (student.getExp() + score >= 500) {
            level = 3;
        }

        student.examReward(score, stage, level);

        return ResponseDto.setSuccess("정답~!");
    }
}
