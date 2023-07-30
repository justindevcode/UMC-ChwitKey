package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ChatSelect extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_select_id")
    Long id;

    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SelectType selectType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public ChatSelect(String content, SelectType selectType, Article article) {
        this.content = content;
        this.selectType = selectType;
        this.article = article;
    }
}
