package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatSelectRepository extends JpaRepository<ChatSelect, Long> {

    boolean existsByArticleAndSelectType(Article article, SelectType selectType);

    Optional<ChatSelect> findByArticleAndSelectType(Article article, SelectType selectType);
}
