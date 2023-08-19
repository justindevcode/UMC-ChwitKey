package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.article.dto.response.ShareArticleRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;


public interface ArticleService {

  DetailArticleRes detailArticle(Long memberId, Long articleIdx);

  Page<SearchArticleRes> searchArticle(String cond, String sortType, Pageable pageable);

  void attendArticle(Long articleId, Long memberId, String type);

  void unAttendArticle(Long articleId, Long memberId, String type);

  Page<SearchArticleRes> searchArticleByKeyword(Long memberId, String keyword, String sortType, Pageable pageable);

  Page<SearchArticleRes> searchArticleByIndustry(Long memberId, String industry, String sortType, Pageable pageable);
}
