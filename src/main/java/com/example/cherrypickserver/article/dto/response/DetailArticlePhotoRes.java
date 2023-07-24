package com.example.cherrypickserver.article.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class DetailArticlePhotoRes {
  private String articleImgUrl;

  @Builder
  public DetailArticlePhotoRes(String articleImgUrl) {
    this.articleImgUrl = articleImgUrl;
  }

  public static DetailArticlePhotoRes toDto(String articleImgUrl) {
    return DetailArticlePhotoRes.builder()
            .articleImgUrl(articleImgUrl)
            .build();
  }
}
