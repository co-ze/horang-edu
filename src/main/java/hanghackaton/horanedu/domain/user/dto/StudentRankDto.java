package hanghackaton.horanedu.domain.user.dto;

import hanghackaton.horanedu.domain.user.entity.Student;
import lombok.Getter;

@Getter
public class StudentRankDto {
    private String name;
    private int rank;
    private int exp;

    public StudentRankDto(Student student) {
        this.name = student.getName();
        this.rank = student.getRanking();
        this.exp = student.getExp();
    }
}
