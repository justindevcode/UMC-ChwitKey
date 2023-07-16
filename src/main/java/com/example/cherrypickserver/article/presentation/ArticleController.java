package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.application.ArticleService;
import com.example.cherrypickserver.article.dto.Request.CreateArticleReq;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

  private final ArticleService articleService;

  // 기사 추가
  @ResponseBody
  @PostMapping("/new")
  public ResponseCustom<Long> createArticle(@RequestBody CreateArticleReq createArticleReq) {
    return ResponseCustom.OK(articleService.createArticle(createArticleReq));
  }
  // 기사 삭제

  // 기사 업데이트

  // 기사 제거
}
