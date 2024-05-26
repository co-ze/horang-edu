package hanghackaton.horanedu.domain.school.entity;

import hanghackaton.horanedu.domain.school.dto.SchoolRequestDto;
import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
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

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private Long teacher;

    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER)
    private List<UserDetail> userDetailList = new ArrayList<>();

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int ranking;

    @Column(nullable = false)
    private String groupCode;

    public School(SchoolRequestDto schoolRequestDto, Long teacher) {
        this.name = schoolRequestDto.getName();
        this.grade = schoolRequestDto.getGrade();
        this.teacher = teacher;
        this.score = 0;
        this.ranking = 0;
        this.groupCode = "-";
    }

    public void setGroupCode(StringBuffer code) {
        this.groupCode = String.valueOf(code);
    }

    public void updateScore(int score) {
        this.score += score;
    }

}