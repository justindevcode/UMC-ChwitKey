package com.example.cherrypickserver.article.dto.request;

import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateArticleReq {
  private String title;
  private String content;
  private String publisher;
  private String uploadedAt;
  private String reporter;
  private String industry;
  private List<CreateArticlePhotosReq> articlePhotos;
}
