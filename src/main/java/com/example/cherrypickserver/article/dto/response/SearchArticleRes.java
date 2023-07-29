package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticlePhoto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SearchArticleRes {
  private String title;
  private String content;
  private String publisher;
  private String reporter;
  private String uploadedAt;
  private List<String> articlePhoto;

  public static SearchArticleRes toDto(Article article) {
    return SearchArticleRes.builder()
            .title(article.getTitle())
            .content(article.getContents())
            .publisher(article.getPublisher())
            .reporter(article.getReporter())
            .uploadedAt(article.getUploadedAt())
            .articlePhoto(article.getArticlePhoto().stream().map(ArticlePhoto::getArticleImgUrl).collect(Collectors.toList()))
            .build();
  }
}
