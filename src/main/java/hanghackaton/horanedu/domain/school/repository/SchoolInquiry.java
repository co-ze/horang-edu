package hanghackaton.horanedu.domain.school.repository;

import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolInquiry {
    Page<SchoolRankDto> schoolRank(Pageable pageable);

}
