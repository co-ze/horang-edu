package hanghackaton.horanedu.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentRequestDto {
    private String name;
    private String school;
    private int chapter;
    private int stage;
    private int exp;
    private int level;

}
