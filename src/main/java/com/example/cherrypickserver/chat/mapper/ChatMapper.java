package com.example.cherrypickserver.chat.mapper;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.chat.domain.Chat;
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
}
