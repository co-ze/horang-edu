package hanghackaton.horanedu.user.dto;

import hanghackaton.horanedu.user.entity.Student;
import lombok.Getter;

@Getter
public class StudentResponseDto {
    private String name;
    private String school;
    private int chapter;
    private int stage;
    private double progress;
    private int exp;
    private int level;

    public StudentResponseDto(Student student) {
        this.name = student.getName();
        this.school = student.getSchool().getName();
        this.chapter = student.getChapter();
        this.stage = student.getStage();
        this.progress = ((double) (student.getStage()-1) / (double) 5 * 100.0);
        this.exp = student.getExp();
        this.level = student.getLevel();
    }
}
