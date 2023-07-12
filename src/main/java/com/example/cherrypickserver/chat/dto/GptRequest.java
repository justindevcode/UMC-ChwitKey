package com.example.cherrypickserver.chat.dto;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GptRequest {

    private final String model = "gpt-3.5-turbo";

    private String role;

    private String message;

    private final Integer maxTokens = 400;

    //GptRequest to ChatCompletionRequest
    public static ChatCompletionRequest of(GptRequest gptRequest) {
        return ChatCompletionRequest.builder()
                .model(gptRequest.getModel())
                .messages(convertChatMessage(gptRequest))
                .maxTokens(gptRequest.getMaxTokens())
                .build();
    }

    //GptRequest에서 ChatCompletion의 messages로 들어갈 부분 convert
    private static List<ChatMessage> convertChatMessage(GptRequest gptRequest) {
        return List.of(new ChatMessage(gptRequest.getRole(), gptRequest.getMessage()));
    }
}
