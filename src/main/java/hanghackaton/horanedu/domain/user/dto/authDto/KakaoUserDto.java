package hanghackaton.horanedu.domain.user.dto.authDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserDto {
    private Long id;
    private String email;
    private String name;
    private String image;

    public KakaoUserDto(Long id, String email, String name, String image) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.image = image;
    }
}
