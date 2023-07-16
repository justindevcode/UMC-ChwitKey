package com.example.cherrypickserver.article.dto.Request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateArticleReq {
  private String articleName;
  private String contents;
  private String publisher;
  private List<String> articleImgUrl;
}
