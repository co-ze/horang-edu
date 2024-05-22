package hanghackaton.horanedu.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int chapter;

    @Column(nullable = false)
    private int stage;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private Boolean answer;

}
