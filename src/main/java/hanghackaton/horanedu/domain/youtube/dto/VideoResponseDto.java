package hanghackaton.horanedu.domain.youtube.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoResponseDto {

    private Long id;
    private Integer playTime;

    public VideoResponseDto(Long id, Integer playTime) {
        this.id = id;
        this.playTime = playTime;
    }
}
