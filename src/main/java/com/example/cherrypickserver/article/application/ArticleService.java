package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;

import java.util.List;

public interface ArticleService {
  Long createArticle(CreateArticleReq createArticleReq);
  List<Article> getArticle();
}
