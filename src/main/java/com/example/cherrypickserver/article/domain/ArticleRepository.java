package com.example.cherrypickserver.article.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  Optional<Article> findByIdAndIsEnable(Long articleId, Boolean isEnable);

  Page<Article> findByTitleContainingOrContentsContainingAndIsEnable(String titleCond, String contentCond, Boolean isEnable, Pageable pageable);

  Page<Article> findByIsEnable(Boolean isEnable, Pageable pageable);

  Page<Article> findByIndustryOrTitleContainingOrContentsContainingAndIsEnable(Industry industry, String titleCond, String contentCond, Boolean isEnable, Pageable pageable);
}
