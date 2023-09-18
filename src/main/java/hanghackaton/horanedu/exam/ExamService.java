package hanghackaton.horanedu.exam;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.user.entity.User;
import hanghackaton.horanedu.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<String> examSolve() {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        int score = 50;
        String stage = "2-5";
        int level = user.getLevel();
        if (user.getExp() + score >= 500) {
            level = 3;
        }

        user.examReward(score, stage, level);

        return ResponseDto.setSuccess("정답~!");
    }
}
