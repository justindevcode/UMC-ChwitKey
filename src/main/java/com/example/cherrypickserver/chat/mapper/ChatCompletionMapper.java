package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.article.domain.Article;
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

    public ChatCompletionRequest fromEntity(Article article) {
        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("user", "다음 기사 내용을 요약해줘 " + article.getContents())))
                .build();
    }
}
