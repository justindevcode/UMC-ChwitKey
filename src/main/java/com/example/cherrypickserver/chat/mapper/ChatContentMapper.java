package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.chat.domain.Chat;
import com.example.cherrypickserver.chat.domain.ChatContent;
import com.example.cherrypickserver.chat.dto.GptResponse;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatContentMapper {

    public ChatContent toEntity(String role, String content, Chat chat) {
        return ChatContent.builder()
                .role(role)
                .content(content)
                .chat(chat)
                .build();
    }

    public ChatContent toEntity(ChatCompletionResult chatCompletionResult, Chat chat) {

        ChatCompletionChoice chatCompletionChoice = chatCompletionResult.getChoices().get(0);
        ChatMessage chatMessage = chatCompletionChoice.getMessage();

        return ChatContent.builder()
                .role(chatMessage.getRole())
                .content(chatMessage.getContent())
                .chat(chat)
                .build();
    }

    public GptResponse fromEntity(ChatContent chatContent) {
        return GptResponse.builder()
                .answer(chatContent.getContent())
                .build();
    }
}
