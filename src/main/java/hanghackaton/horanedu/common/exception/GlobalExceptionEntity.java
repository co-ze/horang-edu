package hanghackaton.horanedu.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GlobalExceptionEntity {
    private final String errorCode;
    private final String errorMessage;

    @Builder
    public GlobalExceptionEntity(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
