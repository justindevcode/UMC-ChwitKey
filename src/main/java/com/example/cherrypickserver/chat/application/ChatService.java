package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.chat.dto.GptRequest;
import com.example.cherrypickserver.chat.dto.GptResponse;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final OpenAiService openAiService;

    public ResponseEntity<?> chatCompletion(GptRequest gptRequest) {

//        List<ChatMessage> chatMessages = new ArrayList<>();
//
//        chatMessages.add(new ChatMessage("system",
//                "당신은 IT 분야에 대한 기사를 작성하는 기자입니다. 10초 이내로 답변하고, IT와 관련되지 않은 질문에는 답변하지 마세요."));

        ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(GptRequest.of(gptRequest));

        GptResponse gptResponse = GptResponse.of(chatCompletionResult);

        return ResponseEntity.ok(gptResponse);
    }
}
