package hanghackaton.horanedu.domain.user.entity;

import hanghackaton.horanedu.domain.school.entity.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column
    private String image;

    @Column
    private String teacherName;

    @Column
    private int grade;

    @Column
    private int classNum;

    public UserDetail(User user) {
        this.user = user;
        this.teacherName = "-";
        this.grade = 0;
        this.classNum = 0;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void updateImage(String image) {
        this.image = image;
    }

    public void setTeacherName(String name) {
        this.teacherName = name;
    }

    public void updateDepartment(int grade, int classNum) {
        this.grade = grade;
        this.classNum = classNum;
    }
}
