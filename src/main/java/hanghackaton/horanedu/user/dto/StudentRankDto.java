package hanghackaton.horanedu.user.dto;

import hanghackaton.horanedu.user.entity.Student;
import lombok.Getter;

@Getter
public class StudentRankDto {
    private String name;
    private int rank;
    private int level;
    private int exp;

    public StudentRankDto(Student student) {
        this.name = student.getName();
        this.rank = student.getRanking();
        this.level = student.getLevel();
        this.exp = student.getExp();
    }
}
