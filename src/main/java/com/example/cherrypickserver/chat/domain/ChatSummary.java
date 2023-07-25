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
public class ChatSummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_summary_id")
    private Long id;

    @Column(columnDefinition="TEXT", nullable = false)
    private String summary;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public ChatSummary(String summary, Article article) {
        this.summary = summary;
        this.article = article;
    }
}
