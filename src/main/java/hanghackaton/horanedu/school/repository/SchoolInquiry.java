package hanghackaton.horanedu.school.repository;

import hanghackaton.horanedu.school.dto.SchoolRankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolInquiry {
    Page<SchoolRankDto> schoolRank(Pageable pageable);

}
