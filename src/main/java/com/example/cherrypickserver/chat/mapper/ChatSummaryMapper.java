package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.chat.domain.ChatSummary;
import com.example.cherrypickserver.chat.dto.GptResponse;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatSummaryMapper {

    public GptResponse fromEntity(ChatSummary chatSummary) {
        return GptResponse.builder()
                .answer(chatSummary.getSummary())
                .build();
    }

    public ChatSummary toEntity(ChatCompletionResult chatCompletionResult, Article article) {

        ChatCompletionChoice chatCompletionChoice = chatCompletionResult.getChoices().get(0);
        ChatMessage chatMessage = chatCompletionChoice.getMessage();

        return ChatSummary.builder()
                .summary(chatMessage.getContent())
                .article(article)
                .build();
    }
}
