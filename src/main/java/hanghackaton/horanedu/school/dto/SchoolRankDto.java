package hanghackaton.horanedu.school.dto;

import hanghackaton.horanedu.school.entity.School;
import lombok.Getter;

@Getter
public class SchoolRankDto {
    private String name;
    private int rank;

    public SchoolRankDto(School school) {
        this.name = school.getName();
        this.rank = school.getRanking();
    }
}
