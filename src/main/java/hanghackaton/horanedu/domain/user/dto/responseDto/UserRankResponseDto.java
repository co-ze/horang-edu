package hanghackaton.horanedu.domain.user.dto.responseDto;

import hanghackaton.horanedu.domain.user.dto.UserRankDto;
import hanghackaton.horanedu.domain.user.entity.UserProgress;
import lombok.Getter;

import java.util.List;

@Getter
public class UserRankResponseDto {
    private String loginUserName;
    private int loginUserRank;
    private int loginUserExp;
    private List<UserRankDto> userRankDtoList;

    public UserRankResponseDto(String name, int rank, int exp, List<UserRankDto> userRankDtoList) {
        this.loginUserName = name;
        this.loginUserRank = rank;
        this.loginUserExp = exp;
        this.userRankDtoList = userRankDtoList;
    }
}
