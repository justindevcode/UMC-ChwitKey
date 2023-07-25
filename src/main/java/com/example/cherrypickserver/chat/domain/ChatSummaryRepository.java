package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatSummaryRepository extends JpaRepository<ChatSummary, Long> {

    boolean existsByArticle(Article article);

    Optional<ChatSummary> findByArticle(Article article);
}
