package hanghackaton.horanedu.domain.gpt.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class ChatGptProperties {

    private final String model = "gpt-3.5-turbo";

    private final Integer maxTokens = 500;

    private final Double temperature = 1.0;

    private final Double topP = 1.0;
}
