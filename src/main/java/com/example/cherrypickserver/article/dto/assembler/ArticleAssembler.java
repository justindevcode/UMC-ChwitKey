package com.example.cherrypickserver.article.dto.assembler;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;

public class ArticleAssembler {
  public Article createArticle(CreateArticleReq createArticleReq) {
    return Article.builder()
            .articleName(createArticleReq.getArticleName())
            .contents(createArticleReq.getContents())
            .publisher(createArticleReq.getPublisher())
            .articleImgKey(createArticleReq.getArticleImgUrl())
            .build();
  }
}
