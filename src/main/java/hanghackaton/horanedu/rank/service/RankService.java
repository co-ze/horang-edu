package hanghackaton.horanedu.rank.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.user.dto.StudentRankDto;
import hanghackaton.horanedu.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RankService {
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public ResponseDto<Page<StudentRankDto>> getPersonalRank(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx_exp");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<StudentRankDto> personalRankPage = studentRepository.studentRank(pageable);

        return ResponseDto.setSuccess("개인 순위", personalRankPage);
    }
}
