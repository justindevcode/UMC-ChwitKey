package com.example.cherrypickserver.chat.mapper;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatCompletionMapper {

    public ChatCompletionRequest fromEntity(List<ChatMessage> chatMessageList) {
        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(chatMessageList)
                .maxTokens(500)
                .build();
    }
}
