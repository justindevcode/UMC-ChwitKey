package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticlePhoto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
            .thumbnailUrl(checkThumbnail(article.getArticlePhoto()))
            .build();
  }

  private static String checkThumbnail(List<ArticlePhoto> articlePhoto) {
    if (articlePhoto.isEmpty()) return null;
    else return articlePhoto.get(0).getArticleImgUrl();
  }
}
