package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.chat.domain.ChatSelect;
import com.example.cherrypickserver.chat.domain.SelectType;
import com.example.cherrypickserver.chat.dto.response.GptResponse;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatSelectMapper {

    public GptResponse fromEntity(ChatSelect chatSelect) {
        return GptResponse.builder()
                .answer(chatSelect.getContent())
                .build();
    }

    public ChatSelect toEntity(ChatCompletionResult chatCompletionResult, Article article, SelectType selectType) {

        ChatCompletionChoice chatCompletionChoice = chatCompletionResult.getChoices().get(0);
        ChatMessage chatMessage = chatCompletionChoice.getMessage();

        return ChatSelect.builder()
                .content(chatMessage.getContent())
                .article(article)
                .selectType(selectType)
                .build();
    }
}
