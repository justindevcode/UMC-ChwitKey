package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticleRepository;
import com.example.cherrypickserver.article.dto.assembler.ArticleAssembler;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

  private final ArticleRepository articleRepository;
  private final ArticleAssembler articleAssembler;
  @Override
  public Long createArticle(CreateArticleReq createArticleReq) {
    return articleRepository.save(articleAssembler.createArticle(createArticleReq)).getId();
  }

  @Override
  public List<Article> getArticle() {
    return articleRepository.findAll();
  }
}
