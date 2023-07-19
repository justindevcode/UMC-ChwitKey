package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {
  Long createArticle(CreateArticleReq createArticleReq);

  DetailArticleRes detailArticle(Long articleIdx);

  Page<SearchArticleRes> searchArticle(String cond, String jobKeyword, Pageable pageable);
}
