package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.chat.domain.SelectType;
import com.example.cherrypickserver.chat.exception.SelectTypeNotFoundException;
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
                .temperature(0.5) //창의성과 일관성 조절
//                .frequencyPenalty(-0.5) //단어의 빈도수를 고려해 문장의 일관성 유지
                .maxTokens(500)
                .build();
    }

    public ChatCompletionRequest fromEntity(Article article, SelectType selectType) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole("user");

        switch (selectType) {
            case SUMMARY:
                chatMessage.setContent("다음 기사 내용을 요약해줘 " + article.getContents());
                break;

            case TRANSLATION:
                chatMessage.setContent("다음 기사 내용을 영어로 번역해줘 " + article.getContents());
                break;

            case KEYWORD:
                chatMessage.setContent("다음 기사 내용에서 중요한 키워드만 뽑아줘 " + article.getContents());
                break;

            default:
                throw new SelectTypeNotFoundException();
        }

        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo-16k")
                .messages(List.of(chatMessage))
                .build();
    }
}
