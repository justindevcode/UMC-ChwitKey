package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.ArticlePhoto;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchArticlePhotoRes {

  private String articleImgUrl;
  private String imgDesc;

  @Builder
  public SearchArticlePhotoRes(String articleImgUrl, String imgDesc) {
    this.articleImgUrl = articleImgUrl;
    this.imgDesc = imgDesc;
  }

  public static SearchArticlePhotoRes toDto(ArticlePhoto articlePhoto) {
    return SearchArticlePhotoRes.builder()
            .articleImgUrl(articlePhoto.getArticleImgUrl())
            .imgDesc(articlePhoto.getImgDesc())
            .build();
  }
}
