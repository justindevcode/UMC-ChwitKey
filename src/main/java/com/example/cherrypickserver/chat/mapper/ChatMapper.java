package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.chat.domain.Chat;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;
import com.example.cherrypickserver.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public Chat toEntity(Member member, Article article) {
        return Chat.builder()
                .member(member)
                .article(article)
                .build();
    }

    public ChatResponse fromEntity(Chat chat) {

        String greeting = "안녕하세요 취트키 GPT입니다. \n\n\""
                + chat.getArticle().getTitle() + "\" \n기사를 보고 오셨네요. \n\n"
                + "기사 내용 중 궁금한 부분이 있다면 물어보세요.";

        return ChatResponse.builder()
                .chatId(chat.getId())
                .greeting(greeting)
                .build();
    }
}
