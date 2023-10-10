package hanghackaton.horanedu.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<T> implements Serializable {
    private HttpStatus statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient T data;

    public static <T> ResponseDto<T> setSuccess(HttpStatus statusCode, String message, T data) {
        return ResponseDto.set(statusCode, message, data);
    }

    public static <T> ResponseDto<T> setSuccess(HttpStatus statusCode, String message) {
        return ResponseDto.set(statusCode, message, null);
    }

    public static <T> ResponseDto<T> setBadRequest(HttpStatus httpStatus, String message) {
        return ResponseDto.set(httpStatus, message, null);
    }

}
