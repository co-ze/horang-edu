package hanghackaton.horanedu.domain.user.dto;

import hanghackaton.horanedu.domain.user.entity.Student;
import lombok.Getter;

@Getter
public class OneStudentRankDto {
    private String name;
    private int level;
    private int rank;
    private int rankInSchool;
    private int exp;

    public OneStudentRankDto(Student student) {
        this.name = student.getName();
        this.level = student.getLevel();
        this.rank = student.getRanking();
        this.rankInSchool = student.getSchoolRank();
        this.exp = student.getExp();
    }
}
