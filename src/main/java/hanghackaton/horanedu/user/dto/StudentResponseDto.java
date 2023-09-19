package hanghackaton.horanedu.user.dto;

import hanghackaton.horanedu.user.entity.Student;
import lombok.Getter;

@Getter
public class StudentResponseDto {
    private String name;
    private String school;
    private String stage;
    private int exp;
    private int level;

    public StudentResponseDto(Student student) {
        this.name = student.getName();
        this.school = student.getSchool().getName();
        this.stage = student.getStage();
        this.exp = student.getExp();
        this.level = student.getLevel();
    }
}
