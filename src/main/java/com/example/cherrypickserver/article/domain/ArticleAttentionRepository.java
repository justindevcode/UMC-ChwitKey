package com.example.cherrypickserver.article.domain;

import com.example.cherrypickserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleAttentionRepository extends JpaRepository<ArticleAttention, Long> {
  Optional<ArticleAttention> findByArticleIdAndMemberIdAndAttentionTypeAndIsEnable(Long articleId, Long memberId, AttentionType type, Boolean isEnable);

  List<ArticleAttention> findByArticleAndMemberAndIsEnable(Article article, Member member, Boolean isEnable);

  List<ArticleAttention> findByMemberAndIsEnable(Member member, Boolean isEnable);
}
