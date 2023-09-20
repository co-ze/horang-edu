package hanghackaton.horanedu.domain.homework.entity;

import hanghackaton.horanedu.domain.homework.dto.HomeworkRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private Boolean checkBox;

    public Homework(HomeworkRequestDto homeworkRequestDto) {
        this.topic = homeworkRequestDto.getTopic();
        this.checkBox = false;
    }

    public void checkOn() {
        this.checkBox = true;
    }

    public void checkOff() {
        this.checkBox = false;
    }
}
