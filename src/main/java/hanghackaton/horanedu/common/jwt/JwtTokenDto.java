package hanghackaton.horanedu.common.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtTokenDto {
    private String accessToken;

    public JwtTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
