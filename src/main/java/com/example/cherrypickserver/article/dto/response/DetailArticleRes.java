package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DetailArticleRes {
  private Long articleId;
  private String title;
  private String content;
  private String publisher;
  private String uploadedAt;
  private String reporter;
  private String industry;
  private String url;
  private List<DetailArticlePhotoRes> articlePhoto;

  public static DetailArticleRes toDto(Article article, String uploadedAt) {
    return DetailArticleRes.builder()
            .articleId(article.getId())
            .title(article.getTitle())
            .content(article.getContents())
            .publisher(article.getPublisher())
            .uploadedAt(uploadedAt)
            .reporter(article.getReporter())
            .industry(article.getIndustry().getValueGpt())
            .url(article.getUrl())
            .articlePhoto(article.getArticlePhoto().stream().map(DetailArticlePhotoRes::toDto).collect(Collectors.toList()))
            .build();
  }
}
