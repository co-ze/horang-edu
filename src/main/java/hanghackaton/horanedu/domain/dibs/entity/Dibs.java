package hanghackaton.horanedu.domain.dibs.entity;

import hanghackaton.horanedu.domain.dibs.dto.DibsRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Dibs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    public Dibs(DibsRequestDto dibsRequestDto) {
        this.topic = dibsRequestDto.getTopic();
    }
}