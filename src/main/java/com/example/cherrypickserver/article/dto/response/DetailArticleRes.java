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
  private String articleName;
  private String contents;
  private String publisher;
  private Date registeredAt;
  private String reporter;
  private List<DetailArticlePhotoRes> articlePhoto;

  public static DetailArticleRes toDto(Article article) {
    return DetailArticleRes.builder()
            .articleName(article.getArticleName())
            .contents(article.getContents())
            .publisher(article.getPublisher())
            .registeredAt(article.getRegisteredAt())
            .reporter(article.getReporter())
            .articlePhoto(article.getArticlePhoto().stream().map(m -> DetailArticlePhotoRes.toDto(m.getArticleImgUrl())).collect(Collectors.toList()))
            .build();
  }
}
