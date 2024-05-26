package hanghackaton.horanedu.domain.school.dto;

import hanghackaton.horanedu.domain.school.entity.School;
import lombok.Getter;

@Getter
public class SchoolRankDto {
    private Long id;
    private String schoolName;
    private int rank;
    private int exp;

    public SchoolRankDto(School school, int rank) {
        this.id = school.getId();
        this.schoolName = school.getName();
        this.rank = rank;
        this.exp = school.getScore();
    }
}
