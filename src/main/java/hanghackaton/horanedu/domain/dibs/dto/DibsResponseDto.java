package hanghackaton.horanedu.domain.dibs.dto;

import hanghackaton.horanedu.domain.dibs.entity.Dibs;
import lombok.Getter;

@Getter
public class DibsResponseDto {
    private Long id;
    private String topic;

    public DibsResponseDto(Dibs dibs) {
        this.id = dibs.getId();
        this.topic = dibs.getTopic();
    }
}
