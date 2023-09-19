package hanghackaton.horanedu.school.entity;

import hanghackaton.horanedu.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.user.entity.Student;
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

    public School(SchoolRequestDto schoolRequestDto) {
        this.name = schoolRequestDto.getName();
        this.score = schoolRequestDto.getScore();
    }
}
