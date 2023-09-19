package hanghackaton.horanedu.user.repository;

import hanghackaton.horanedu.user.dto.StudentRankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentInquiry {
    Page<StudentRankDto> studentRank(Pageable pageable);
}
