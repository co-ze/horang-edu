package hanghackaton.horanedu.domain.gpt.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private String role;
    private String content;

}
