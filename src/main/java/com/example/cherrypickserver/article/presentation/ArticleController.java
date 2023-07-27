package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.application.ArticleService;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

  private final ArticleService articleService;

  // 기사 추가
  @ResponseBody
  @PostMapping("/new")
  public ResponseCustom<Long> createArticle(@RequestBody CreateArticleReq createArticleReq) throws ParseException {
    return ResponseCustom.OK(articleService.createArticle(createArticleReq));
  }

  // 기사 상세 조회
  @ResponseBody
  @GetMapping("/detail/{articleId}")
  public ResponseCustom<DetailArticleRes> detailArticle(@PathVariable Long articleId) {
    return ResponseCustom.OK(articleService.detailArticle(articleId));
  }

  //  // 기사 검색 (커맨드 + 정렬)
  @ResponseBody
  @GetMapping("/search")
  public ResponseCustom<Page<SearchArticleRes>> searchArticle(
          @RequestParam String cond,
          @RequestParam String sortType,
          Pageable pageable
  )
  {
    return ResponseCustom.OK(articleService.searchArticle(cond, sortType, pageable));
  }

  // 좋아요 + 스크랩
  @ResponseBody
  @GetMapping("/like/{articleId}")
  public ResponseCustom<Void> attendArticle(
          @PathVariable Long articleId,
          @RequestParam Long memberId,
          @RequestParam String type
          )
  {
    articleService.attendArticle(articleId, memberId, type);
    return ResponseCustom.OK();
  }
}
