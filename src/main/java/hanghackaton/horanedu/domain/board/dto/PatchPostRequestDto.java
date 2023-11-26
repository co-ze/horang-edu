package hanghackaton.horanedu.domain.board.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class PatchPostRequestDto {

    @Nullable
    private String title;
    @Nullable
    private String content;

}
