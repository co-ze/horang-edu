package hanghackaton.horanedu.domain.user.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TempSignupResponseDto {
    private String email;
    private String password;

    public TempSignupResponseDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
