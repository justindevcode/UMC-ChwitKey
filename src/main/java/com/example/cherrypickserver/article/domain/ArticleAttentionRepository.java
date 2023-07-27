package com.example.cherrypickserver.article.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleAttentionRepository extends JpaRepository<ArticleAttention, Long> {
  Optional<ArticleAttention> findByArticleIdAndMemberIdAndAttentionTypeAndIsEnable(Long articleId, Long memberId, AttentionType type, Boolean isEnable);
}
