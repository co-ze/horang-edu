package hanghackaton.horanedu.domain.gpt.service;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.exception.ChatException;
import hanghackaton.horanedu.domain.gpt.dto.ChatRequest;
import hanghackaton.horanedu.domain.gpt.dto.ChatResponse;
import hanghackaton.horanedu.domain.gpt.dto.Message;
import hanghackaton.horanedu.domain.gpt.property.ChatGptProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatCompletionService {

    private static final String url = "https://api.openai.com/v1/chat/completions";

    @Value("${api.key}")
    private static String key;
    private static final String AUTHORIZATION = "Bearer " + key;

    private final ChatGptProperties chatGptProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseDto<ChatResponse> sendRequestAndGetResponse(String prompt) {
        Message message = Message.builder()
                .role("user")
                .content(prompt)
                .build();

        List<Message> messages = new ArrayList<>();
        messages.add(message);
        ChatRequest request = ChatRequest.builder()
                .model(chatGptProperties.getModel())
                .messages(messages)
                .build();

        ChatResponse response = getResponse(buildHttpEntity(request), ChatResponse.class, url);
        try {
            return ResponseDto.set(HttpStatus.OK, "채팅 성공", response);
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    protected <T> HttpEntity<?> buildHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", AUTHORIZATION);
        return new HttpEntity<>(request, headers);
    }

    protected <T> T getResponse(HttpEntity<?> httpEntity, Class<T> responseType, String url) {
        log.info("request url: {}, httpEntity: {}", url, httpEntity);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value()) {
            log.error("error response status: {}", responseEntity);
            throw new ChatException("error response status :" + responseEntity.getStatusCodeValue());
        } else {
            log.info("response: {}", responseEntity);
        }
        return responseEntity.getBody();
    }
}
