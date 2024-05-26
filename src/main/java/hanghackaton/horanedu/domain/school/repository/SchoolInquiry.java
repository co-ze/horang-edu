package hanghackaton.horanedu.domain.school.repository;

import hanghackaton.horanedu.domain.school.dto.SchoolRankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolInquiry {

    List<SchoolRankDto> getSchoolRank();

    int getLoginUserSchoolRank(List<SchoolRankDto> ranking, Long id);

}
