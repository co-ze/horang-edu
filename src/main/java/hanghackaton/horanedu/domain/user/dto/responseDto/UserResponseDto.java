package hanghackaton.horanedu.domain.user.dto.responseDto;

import hanghackaton.horanedu.domain.school.entity.School;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String name;
    private String school;
    private String role;
    private String image;

    public UserResponseDto(String email, String name, School school, UserRole role, String image) {
        this.email = email;
        this.name = name;
        this.school = school.getName();
        this.role = role.name();
        this.image = image;
    }
}
