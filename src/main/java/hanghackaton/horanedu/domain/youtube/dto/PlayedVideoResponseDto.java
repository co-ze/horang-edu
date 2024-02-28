package hanghackaton.horanedu.domain.youtube.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class PlayedVideoResponseDto {

    private Map<Long, Integer> playedMap;

    public PlayedVideoResponseDto(Map<Long, Integer> playedMap) {
        this.playedMap = playedMap;
    }
}
