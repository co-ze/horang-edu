package hanghackaton.horanedu.domain.user.dto.authDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupDto {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "이메일 형식을 확인해주세요!")
    @NotNull(message = "이메일을 입력해주세요!")
    private String email;

    @Size(min = 8, max = 15, message = "비밀 번호는 최소 8자에서 15자까지 가능 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,}$",
            message = "비밀 번호는 대문자,소문자,숫자,특수 문자(!@#$%^&*)만 가능 합니다.")
//    ^ : 문자열 시작 $ : 문자열 끝
//    [a-zA-Z\\p{Punct}] : 알파벳 대소문자와 특수문자를 허용
//    \\p{Punct}는 Unicode punctuation character를 나타내며, 이에는 대부분의 특수문자가 포함
//    *로 이 문자열이 0개 이상인지 여부, 공백을 포함하지 않음
    @NotNull(message = "비밀 번호를 입력해주세요!")
    private String password;

    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,10}$", message = "이름은 한글, 영어, 숫자로 최대 10글자 이하로 입력해주세요!")
    @NotNull(message = "이름을 입력해주세요!")
    private String name;

    private boolean role;
}
