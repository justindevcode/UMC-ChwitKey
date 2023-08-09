package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.application.ArticleService;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import com.example.cherrypickserver.global.resolver.Auth;
import com.example.cherrypickserver.global.resolver.IsLogin;
import com.example.cherrypickserver.global.resolver.LoginStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  @Operation(summary = "기사 추가 요청", description = "기사 정보가 저장됩니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 추가 성공"),
  })
  @ResponseBody
  @PostMapping("/new")
  public ResponseCustom<Long> createArticle(@RequestBody CreateArticleReq createArticleReq) throws ParseException {
    return ResponseCustom.OK(articleService.createArticle(createArticleReq));
  }

  // 기사 상세 조회
  @Operation(summary = "기사 상세조회 요청", description = "기사 상세 정보를 조회합니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 상세조회 성공"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 기사"),
  })
  @ResponseBody
  @GetMapping("/detail/{articleId}")
  public ResponseCustom<DetailArticleRes> detailArticle(@Parameter(description = "기사 id") @PathVariable Long articleId) {
    return ResponseCustom.OK(articleService.detailArticle(articleId));
  }

  //  // 기사 검색 (커맨드 + 정렬)
  @Operation(summary = "기사 검색 요청", description = "커맨드와 정렬에 의한 기사 검색 결과를 조회합니다. 정렬은 sortType(ASC, DESC, LIKE) 오름차순, 내림차순, 인기순으로 구분됩니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 검색 성공"),
  })
  @ResponseBody
  @GetMapping("/search")
  public ResponseCustom<Page<SearchArticleRes>> searchArticle(
          @Parameter(description = "검색어") @RequestParam String cond,
          @Parameter(description = "ASC(오름차순), DESC(내림차순), LIKE(인기순)")@RequestParam String sortType,
          Pageable pageable
  )
  {
    return ResponseCustom.OK(articleService.searchArticle(cond, sortType, pageable));
  }

  // 기사 검색 (키워드 + 정렬)
  @Operation(summary = "기사 검색 요청", description = "키워드와 정렬에 의한 기사 검색 결과를 조회합니다. 정렬은 sortType(ASC, DESC, LIKE) 오름차순, 내림차순, 인기순으로 구분됩니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 검색 성공"),
  })
  @Auth
  @ResponseBody
  @GetMapping("/search/keyword")
  public ResponseCustom<Page<SearchArticleRes>> searchArticleByKeyword(
          @Parameter(description = "멤버 id") @IsLogin LoginStatus loginStatus,
          @Parameter(description = "ASC(오름차순), DESC(내림차순), LIKE(인기순)") @RequestParam String sortType,
          @Parameter(description = "멤버가 등록한 키워드") @RequestParam String keyword,
          Pageable pageable
  )
  {
    return ResponseCustom.OK(articleService.searchArticleByKeyword(loginStatus.getMemberId(), keyword, sortType, pageable));
  }

  // 기사 검색 (직군 + 정렬)
  @Operation(summary = "기사 검색 요청", description = "직군과 정렬에 의한 기사 검색 결과를 조회합니다. 정렬은 sortType(ASC, DESC, LIKE)에 따라 오름차순, 내림차순, 인기순으로 구분됩니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "기사 검색 성공"),
  })
  @ResponseBody
  @GetMapping("/search/industry")
  public ResponseCustom<Page<SearchArticleRes>> searchArticleByIndustry(
          @Parameter(description = "멤버 id") @IsLogin LoginStatus loginStatus,
          @Parameter(description = "ASC(오름차순), DESC(내림차순), LIKE(인기순)") @RequestParam String sortType,
          @Parameter(description = "멤버가 등록한 직군") @RequestParam String industry,
          Pageable pageable
  )
  {
    return ResponseCustom.OK(articleService.searchArticleByIndustry(loginStatus.getMemberId(), industry, sortType, pageable));
  }

  // 좋아요 + 스크랩
  @Operation(summary = "관심 기사 요청", description = "관심 기사 등록을 요청합니다. type(like, scrap)에 따라 '좋아요'와 '스크랩' 요청으로 구분됩니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "관심 기사 요청 성공"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 기사"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 회원"),
          @ApiResponse(responseCode = "400", description = "존재하지 Attention type"),
          @ApiResponse(responseCode = "400", description = "이미 좋아요 또는 스크랩한 기사"),
  })
  @ResponseBody
  @PostMapping("/like/{articleId}")
  public ResponseCustom<Void> attendArticle(
          @Parameter(description = "기사 id") @PathVariable Long articleId,
          @Parameter(description = "멤버 id") @IsLogin LoginStatus loginStatus,
          @Parameter(description = "좋아요(like), 스크랩(scrap)") @RequestParam String type
          )
  {
    articleService.attendArticle(articleId, loginStatus.getMemberId(), type);
    return ResponseCustom.OK();
  }

  // 좋아요 취소 + 스크랩 취소
  @Operation(summary = "관심 기사 취소 요청", description = "관심 등록 취소를 요청합니다. type(like, scrap)에 따라 '좋아요'와 '스크랩' 취소 요청으로 구분됩니다.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "관심 등록 취소 성공"),
          @ApiResponse(responseCode = "404", description = "좋아요 또는 스크랩한 기사가 아님"),
          @ApiResponse(responseCode = "400", description = "존재하지 않는 Attention type")
  })
  @ResponseBody
  @DeleteMapping("/unlike/{articleId}")
  public ResponseCustom<Void> unAttendArticle(
          @Parameter(description = "기사 id") @PathVariable Long articleId,
          @Parameter(description = "멤버 id") @IsLogin LoginStatus loginStatus,
          @Parameter(description = "좋아요(like), 스크랩(scrap)") @RequestParam String type
  )
  {
    articleService.unAttendArticle(articleId, loginStatus.getMemberId(), type);
    return ResponseCustom.OK();
  }
}
