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

    public UserDetail(User user) {
        this.user = user;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
