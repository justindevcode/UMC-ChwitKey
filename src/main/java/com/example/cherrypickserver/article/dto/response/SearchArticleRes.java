package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticlePhoto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SearchArticleRes {
  private Long articleId;
  private String title;
  private String content;
  private String publisher;
  private String reporter;
  private String uploadedAt;
  private String industry;
  private String url;
  private List<SearchArticlePhotoRes> articlePhoto;

  public static SearchArticleRes toDto(Article article, String uploadedAt) {
    return SearchArticleRes.builder()
            .articleId(article.getId())
            .title(article.getTitle())
            .content(article.getContents())
            .publisher(article.getPublisher())
            .reporter(article.getReporter())
            .uploadedAt(uploadedAt)
            .industry(article.getIndustry().getValueGpt())
            .url(article.getUrl())
            .articlePhoto(article.getArticlePhoto().stream().map(SearchArticlePhotoRes::toDto).collect(Collectors.toList()))
            .build();
  }
}
