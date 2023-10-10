package hanghackaton.horanedu.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int chapter;

    @Column(nullable = false)
    private int stage;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private int level;

    public UserProgress(User user) {
        this.user = user;
        this.chapter = 1;
        this.stage = 1;
        this.exp = 0;
        this.level = 0;
    }
}
