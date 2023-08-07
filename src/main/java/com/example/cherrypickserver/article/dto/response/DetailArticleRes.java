package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DetailArticleRes {
  private String title;
  private String content;
  private String publisher;
  private Date uploadedAt;
  private String reporter;
  private List<DetailArticlePhotoRes> articlePhoto;

  public static DetailArticleRes toDto(Article article) {
    return DetailArticleRes.builder()
            .title(article.getTitle())
            .content(article.getContents())
            .publisher(article.getPublisher())
            .uploadedAt(article.getUploadedAt())
            .reporter(article.getReporter())
            .articlePhoto(article.getArticlePhoto().stream().map(DetailArticlePhotoRes::toDto).collect(Collectors.toList()))
            .build();
  }
}
