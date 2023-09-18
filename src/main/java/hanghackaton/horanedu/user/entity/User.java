package hanghackaton.horanedu.user.entity;

import hanghackaton.horanedu.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String stage;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private int level;

    @Builder
    public User(Long id, String name, String stage, int exp, int level, String school) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.stage = stage;
        this.exp = exp;
        this.level = level;
    }

    public User(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        this.school = userRequestDto.getSchool();
        this.stage = userRequestDto.getStage();
        this.exp = userRequestDto.getExp();
        this.level = userRequestDto.getLevel();
    }

    public void examReward(int exp, String stage, int level) {
        this.exp += exp;
        this.stage = stage;
        this.level = level;
    }
}
