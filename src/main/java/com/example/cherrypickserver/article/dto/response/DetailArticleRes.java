package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailArticleRes {
  private String articleName;
  private String contents;
  private String publisher;
  private String articleImgUrl;

  public static DetailArticleRes toDto(Article article) {
    return DetailArticleRes.builder()
            .articleName(article.getArticleName())
            .contents(article.getContents())
            .publisher(article.getPublisher())
            .articleImgUrl(article.getArticleImgKey())
            .build();
  }
}
