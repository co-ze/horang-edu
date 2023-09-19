package hanghackaton.horanedu.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentRequestDto {
    private String name;
    private String school;
    private String stage;
    private int exp;
    private int level;

    @Builder
    public StudentRequestDto(String name, String school, String stage, int exp, int level) {
        this.name = name;
        this.school = school;
        this.stage = stage;
        this.exp = exp;
        this.level = level;
    }
}
