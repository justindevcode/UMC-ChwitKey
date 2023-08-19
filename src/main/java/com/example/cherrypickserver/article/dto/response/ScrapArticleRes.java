package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScrapArticleRes {
  private Long articleId;
  private String title;
  private String publisher;
  private String uploadedAt;
  private String thumbnailUrl;

  public static ScrapArticleRes toDto(Article article, String uploadedAt) {
    return ScrapArticleRes.builder()
            .articleId(article.getId())
            .title(article.getTitle())
            .publisher(article.getPublisher())
            .uploadedAt(uploadedAt)
            .thumbnailUrl(article.getArticlePhoto().get(0).getArticleImgUrl())
            .build();
  }
}
