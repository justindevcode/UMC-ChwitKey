package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;


public interface ArticleService {
  Long createArticle(CreateArticleReq createArticleReq) throws ParseException;

  DetailArticleRes detailArticle(Long articleIdx);

  Page<SearchArticleRes> searchArticle(String cond, String sortType, Pageable pageable);

  void likeArticle(Long articleId, Long memberId);
}
