package hanghackaton.horanedu.domain.user.repository;

import hanghackaton.horanedu.domain.user.dto.UserRankDto;
import hanghackaton.horanedu.domain.user.dto.responseDto.UserRankResponseDto;
import hanghackaton.horanedu.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserInquiry {
    Optional<User> findUserById(Long id);

    List<UserRankDto> getUserRank();

    int getLoginUserRank(List<UserRankDto> ranking, Long id);
}
