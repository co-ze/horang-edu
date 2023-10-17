package hanghackaton.horanedu.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Choice implements Serializable {

    private Integer index;
    private Message text;
    private String logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;

}
