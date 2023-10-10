package hanghackaton.horanedu.domain.user.dto.responseDto;

import hanghackaton.horanedu.domain.user.entity.User;
import hanghackaton.horanedu.domain.user.entity.UserDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchUserResponseDto {
    private Long id;
    private String name;
    private String school;
    private String image;

    public PatchUserResponseDto(User user, UserDetail userDetail) {
        this.id = user.getId();
        this.name = user.getName();
        this.school = userDetail.getSchool().getName();
        this.image = userDetail.getImage();
    }
}
