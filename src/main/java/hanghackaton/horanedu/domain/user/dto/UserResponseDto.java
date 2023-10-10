package hanghackaton.horanedu.domain.user.dto;

import hanghackaton.horanedu.domain.user.entity.UserDetail;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String school;
    private int chapter;
    private int stage;
    private int progress;
    private int exp;
    private int level;

}
