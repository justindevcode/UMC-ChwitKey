package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.application.ArticleService;
import com.example.cherrypickserver.article.dto.response.ShareArticleRes;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Tag(name = "Article Controller", description = "기사 관련 컨드롤러") // Contoller 정보 설정
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

  // 좋아요 취소 + 스크랩 취소
  @ResponseBody
  @GetMapping("/unlike/{articleId}")
  public ResponseCustom<Void> unAttendArticle(
          @PathVariable Long articleId,
          @RequestParam Long memberId,
          @RequestParam String type
  )
  {
    articleService.unAttendArticle(articleId, memberId, type);
    return ResponseCustom.OK();
  }

  // 기사 공유
  @ResponseBody
  @GetMapping("/share/{articleId}")
  public ResponseCustom<ShareArticleRes> shareArticle(
          @PathVariable Long articleId
  )
  {
    return ResponseCustom.OK(articleService.shareArticle(articleId));
  }

}
