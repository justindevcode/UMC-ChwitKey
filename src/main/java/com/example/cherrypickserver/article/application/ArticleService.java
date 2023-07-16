package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.dto.Request.CreateArticleReq;

public interface ArticleService {
  Long createArticle(CreateArticleReq createArticleReq);
}
