package hanghackaton.horanedu.domain.school.dto;

import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.userEnum.UserRole;
import lombok.Getter;

@Getter
public class UserListDto {
    private Long id;
    private String name;
    private UserRole role;

    public UserListDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
