package hanghackaton.horanedu.domain.gpt.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.domain.gpt.dto.ChatResponse;
import hanghackaton.horanedu.domain.gpt.dto.MessageDto;
import hanghackaton.horanedu.domain.gpt.service.ChatCompletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatCompletionController {

    private final ChatCompletionService chatCompletionService;

    @PostMapping
    public ResponseDto<ChatResponse> sendRequestAndGetResponse (@RequestBody MessageDto messageDto) {
        return chatCompletionService.sendRequestAndGetResponse(messageDto.getPrompt());
    }
}
