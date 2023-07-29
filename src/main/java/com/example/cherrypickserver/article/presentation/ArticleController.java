package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.application.ArticleService;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Tag(name = "Article Controller", description = "기사 관련 컨드롤러")
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

  private final ArticleService articleService;

  // 기사 추가
  @Operation(summary = "기사 추가 요청", description = "기사 정보가 저장됩니다.", tags = {"Article Controller"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 추가 성공"),
  })
  @ResponseBody
  @PostMapping("/new")
  public ResponseCustom<Long> createArticle(@RequestBody CreateArticleReq createArticleReq) throws ParseException {
    return ResponseCustom.OK(articleService.createArticle(createArticleReq));
  }

  // 기사 상세 조회
  @Operation(summary = "기사 상세조회 요청", description = "기사 상세 정보를 조회합니다.", tags = {"Article Controller"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 상세조회 성공"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 기사"),
  })
  @ResponseBody
  @GetMapping("/detail/{articleId}")
  public ResponseCustom<DetailArticleRes> detailArticle(@PathVariable Long articleId) {
    return ResponseCustom.OK(articleService.detailArticle(articleId));
  }

  //  // 기사 검색 (커맨드 + 정렬)
  @Operation(summary = "기사 검색 요청", description = "커맨드와 정렬에 의한 검색 결과를 조회합니다.", tags = {"Article Controller"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 검색 성공"),
  })
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
  @Operation(summary = "관심 기사 요청", description = "관심 기사 등록을 요청합니다. type(like, scrap)에 따라 '좋아요'와 '스크랩' 요청으로 구분됩니다.", tags = {"Article Controller"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "관심 기사 요청 성공"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 기사"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 회원"),
          @ApiResponse(responseCode = "400", description = "존재하지 Attention type"),
          @ApiResponse(responseCode = "400", description = "이미 좋아요 또는 스크랩한 기사"),
  })
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
  @Operation(summary = "관심 기사 취소 요청", description = "관심 등록 취소를 요청합니다. type 파라미터에 대한 설명은 '관심 기사 등록' 요청을 참조해주세요.", tags = {"Article Controller"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "관심 등록 취소 성공"),
          @ApiResponse(responseCode = "404", description = "좋아요 또는 스크랩한 기사가 아님"),
          @ApiResponse(responseCode = "400", description = "존재하지 않는 Attention type")
  })
  @ResponseBody
  @DeleteMapping("/unlike/{articleId}")
  public ResponseCustom<Void> unAttendArticle(
          @PathVariable Long articleId,
          @RequestParam Long memberId,
          @RequestParam String type
  )
  {
    articleService.unAttendArticle(articleId, memberId, type);
    return ResponseCustom.OK();
  }
}
