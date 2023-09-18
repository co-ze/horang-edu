package hanghackaton.horanedu.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<T> implements Serializable {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient T data;

    public static <T> ResponseDto<T> setSuccess(String message, T data) {
        return ResponseDto.set(message, data);
    }

    public static <T> ResponseDto<T> setSuccess(String message) {
        return ResponseDto.set(message, null);
    }

}
