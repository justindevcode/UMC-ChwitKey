package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.application.ArticleService;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

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

  // 기사 상세 조회
  @ResponseBody
  @GetMapping("/detail/{articleIdx}")
  public ResponseCustom<DetailArticleRes> detailArticle(@PathVariable Long articleIdx) {
    return ResponseCustom.OK(articleService.detailArticle(articleIdx));
  }

  // 기사 검색 (커맨드)
  @ResponseBody
  @GetMapping("/search")
  public ResponseCustom<Page<SearchArticleRes>> searchArticleByCond(
          @RequestParam String cond,
          @RequestParam String jobKeyword,
          Pageable pageable)
  {
    if (StringUtils.hasText(cond) && StringUtils.hasText(jobKeyword))
      return  ResponseCustom.OK(articleService.searchArticleByCondAndJobKeyword(cond, jobKeyword, pageable));
    else return ResponseCustom.OK(articleService.searchArticleByCond(cond, pageable));
  }
}
