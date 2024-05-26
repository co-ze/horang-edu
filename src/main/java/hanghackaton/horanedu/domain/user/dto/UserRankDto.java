package hanghackaton.horanedu.domain.user.dto;

import hanghackaton.horanedu.domain.user.entity.UserProgress;
import lombok.Getter;

@Getter
public class UserRankDto {
    private Long id;
    private String userName;
    private int rank;
    private int exp;

    public UserRankDto(UserProgress userProgress, int rank) {
        this.id = userProgress.getId();
        this.userName = userProgress.getUser().getName();
        this.rank = rank;
        this.exp = userProgress.getExp();
    }

}
