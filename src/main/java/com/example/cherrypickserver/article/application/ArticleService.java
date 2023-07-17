package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleService {
  Long createArticle(CreateArticleReq createArticleReq);

  DetailArticleRes detailArticle(Long articleIdx);

  Page<SearchArticleRes> searchArticleByCond(String cond, Pageable pageable);

  Page<SearchArticleRes> searchArticleByCondAndJobKeyword(String cond, String jobKeyword, Pageable pageable);
}
