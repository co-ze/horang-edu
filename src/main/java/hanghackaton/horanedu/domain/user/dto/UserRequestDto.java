package hanghackaton.horanedu.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String school;
    private int chapter;
    private int stage;
    private int exp;
    private int level;

}
