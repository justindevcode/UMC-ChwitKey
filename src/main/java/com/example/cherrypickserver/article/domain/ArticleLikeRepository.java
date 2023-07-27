package com.example.cherrypickserver.article.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleAttention, Long> {
}
