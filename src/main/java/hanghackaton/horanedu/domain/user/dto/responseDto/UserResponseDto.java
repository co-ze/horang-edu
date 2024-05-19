package hanghackaton.horanedu.domain.user.dto.responseDto;

import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String school;
    private String role;
    private String image;
    private int level;
    private int chapter;
    private int exp;

    public UserResponseDto(Long id, String email, String name, String schoolName, UserRole role, String image,
                           int level, int chapter, int exp) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.school = schoolName;
        this.role = role.name();
        this.image = image;
        this.level = level;
        this.chapter = chapter;
        this.exp = exp;
    }
}
