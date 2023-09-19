package hanghackaton.horanedu.user.entity;

import hanghackaton.horanedu.school.entity.School;
import hanghackaton.horanedu.user.dto.StudentRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
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
    private String stage;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int ranking;

    @Builder
    public Student(Long id, String name, String stage, int exp, int level) {
        this.id = id;
        this.name = name;
        this.stage = stage;
        this.exp = exp;
        this.level = level;
    }

    public Student(StudentRequestDto studentRequestDto, School school) {
        this.name = studentRequestDto.getName();
        this.stage = studentRequestDto.getStage();
        this.school = school;
        this.exp = studentRequestDto.getExp();
        this.level = studentRequestDto.getLevel();
        this.ranking = 0;
    }

    public void examReward(int exp, String stage, int level) {
        this.exp += exp;
        this.stage = stage;
        this.level = level;
    }

    public void updateRank(int rank) {
        this.ranking = rank;
    }
}
