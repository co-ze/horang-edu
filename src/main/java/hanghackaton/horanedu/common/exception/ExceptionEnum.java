package hanghackaton.horanedu.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "아이디 또는 비밀번호가 일치하지 않습니다."),
    PRESENT_PASSWORD(HttpStatus.BAD_REQUEST, "400", "입력한 비밀번호와 기존 비밀번호가 일치하지 않습니다."),
    PASSWORD_REGEX(HttpStatus.BAD_REQUEST, "400", "비밀번호는 8~15자리, 영어, 숫자, 특수문자 조합으로 구성되어야 합니다."),
    NICKNAME_REGEX(HttpStatus.BAD_REQUEST, "400", "닉네임은 10자리이내로 문자, 숫자 조합으로 구성되어야합니다."),
    EMAIL_REGEX(HttpStatus.BAD_REQUEST, "400", "이메일 형식이 올바르지 않습니다."),
    EMAIL_BAD_SEND(HttpStatus.BAD_REQUEST, "400", "메일 전송에 실패하였습니다."),
    USER_INFORMATION(HttpStatus.BAD_REQUEST, "400", "변경할 회원정보를 작성하지 않았습니다."),
    COINCIDE_PASSWORD(HttpStatus.BAD_REQUEST, "400", "새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    REQUEST_DATA_BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "요청 데이터가 NULL 인지 확인 하세요."),
    PORTFOLIO_ID_LIST_IS_NULL(HttpStatus.BAD_REQUEST, "400", "portfolioIdList 가 null 입니다. portfolioRequestDto 에 추가하세요."),

    // 401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "권한이 없습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 token입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "401", "Expired JWT token, 만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "401", "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    JWT_CLAIMS_IS_EMPTY(HttpStatus.UNAUTHORIZED, "401", "JWT claims is empty, 잘못된 JWT 토큰 입니다."),
    NOT_JOIN_GROUP(HttpStatus.UNAUTHORIZED, "401", "학급에 가입해 주세요."),

    //403 Forbidden
    PROJECT_FORBIDDEN(HttpStatus.FORBIDDEN, "403", "작성자의 프로젝트만 포트폴리오에 추가할 수 있습니다."),
    LOGIN_FORBIDDEN(HttpStatus.FORBIDDEN, "403", "이메일 또는 비밀번호가 일치하지 않습니다."),

    // 404 Not Found
    NOT_FOUND_SCHOOL(HttpStatus.NOT_FOUND, "404", "학교가 존재하지 않습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "404_2", "댓글이 존재하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "404", "회원이 존재하지 않습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "404", "글이 존재하지 않습니다."),
    NOT_FOUND_TEACHER(HttpStatus.NOT_FOUND,"404", "선생님이 존재하지 않습니다."),
    NOT_FOUND_VIDEO(HttpStatus.NOT_FOUND, "404", "비디오 재생목록 리스트가 존재하지 않습니다. 제대로 생성됐는지 확인해보세요."),
    NOT_FOUND_GRADE(HttpStatus.NOT_FOUND, "404", "학급 게시물은 학급을 생성한 후에 작성할 수 있습니다."),

    // 409 Conflict
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "409", "중복된 아이디가 이미 존재합니다."),
    DUPLICATED_NICK_NAME(HttpStatus.CONFLICT, "409", "중복된 닉네임이 이미 존재합니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "409", "중복된 이메일이 이미 존재합니다."),
    EXISTED_NICK_NAME(HttpStatus.CONFLICT, "409", "기존 닉네임과 동일합니다."),
    USER_IS_DELETED(HttpStatus.CONFLICT, "409", "회원 탈퇴가 된 상태입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
