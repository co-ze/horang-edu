package hanghackaton.horanedu.domain.user.entity;

import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.dto.StudentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false)
    private int chapter;

    @Column(nullable = false)
    private int stage;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int ranking;

    @Column(nullable = false)
    private int schoolRank;

    public Student(StudentRequestDto studentRequestDto, School school) {
        this.name = studentRequestDto.getName();
        this.chapter = studentRequestDto.getChapter();
        this.stage = studentRequestDto.getStage();
        this.school = school;
        this.exp = studentRequestDto.getExp();
        this.level = studentRequestDto.getLevel();
        this.ranking = 0;
        this.schoolRank = 0;
    }

    public void examReward(int exp, int chapter, int stage, int level) {
        this.exp = exp;
        this.chapter = chapter;
        this.stage = stage;
        this.level = level;
    }

    public void attendanceReward(int exp, int level) {
        this.exp = exp;
        this.level = level;
    }

    public void updateRank(int rank) {
        this.ranking = rank;
    }

    public void updateSchoolRank(int rank) {
        this.schoolRank = rank;
    }

    public void resetButton() {
        this.chapter = 1;
        this.stage = 1;
        this.exp = 0;
        this.level = 1;
    }
}
