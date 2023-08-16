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
  private Long articleId;
  private String title;
  private String content;
  private String publisher;
  private String reporter;
  private Date uploadedAt;
  private String industry;
  private List<SearchArticlePhotoRes> articlePhoto;

  public static SearchArticleRes toDto(Article article) {
    return SearchArticleRes.builder()
            .articleId(article.getId())
            .title(article.getTitle())
            .content(article.getContents())
            .publisher(article.getPublisher())
            .reporter(article.getReporter())
            .uploadedAt(article.getUploadedAt())
            .industry(article.getIndustry().getValueGpt())
            .articlePhoto(article.getArticlePhoto().stream().map(SearchArticlePhotoRes::toDto).collect(Collectors.toList()))
            .build();
  }
}
