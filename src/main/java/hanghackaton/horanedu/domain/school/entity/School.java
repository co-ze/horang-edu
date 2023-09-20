package hanghackaton.horanedu.domain.school.entity;

import hanghackaton.horanedu.domain.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.domain.user.entity.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<Student> studentList = new ArrayList<>();

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int ranking;

    public School(SchoolRequestDto schoolRequestDto) {
        this.name = schoolRequestDto.getName();
        this.score = 0;
        this.ranking = 0;
    }

    public void updateRank(int rank) {
        this.ranking = rank;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void resetScore(int score) {
        this.score -= score;
    }
}