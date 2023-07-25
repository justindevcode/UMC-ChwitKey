package com.example.cherrypickserver.article.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateArticleReq {
  private String title;
  private String content;
  private String publisher;
  private String registeredAt;
  private String reporter;
  private List<CreateArticlePhotosReq> articlePhotos;
}
