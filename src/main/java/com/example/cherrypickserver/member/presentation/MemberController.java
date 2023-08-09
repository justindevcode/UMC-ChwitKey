package com.example.cherrypickserver.member.presentation;

import com.example.cherrypickserver.global.dto.ResponseCustom;
import com.example.cherrypickserver.global.resolver.Auth;
import com.example.cherrypickserver.global.resolver.IsLogin;
import com.example.cherrypickserver.global.resolver.LoginStatus;
import com.example.cherrypickserver.member.application.MemberService;
import com.example.cherrypickserver.member.dto.request.PostSignUpReq;
import com.example.cherrypickserver.member.dto.response.LoginTokenRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member Controller", description = "회원 컨드롤러")
@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 카카오 로그인 콜백
    @ResponseBody
    @GetMapping("/callback/kakao")
    public ResponseCustom<LoginTokenRes> kakaoCallback(@RequestParam String code)
    {
        return ResponseCustom.OK(memberService.kakaoLogin(code));
    }

    // 첫 회원 프로필 설정
    @Auth
    @ResponseBody
    @PostMapping("/signUp")
    public ResponseCustom<LoginTokenRes> signUp(
            @IsLogin LoginStatus loginStatus,
            @RequestBody PostSignUpReq postSignUpReq
    )
    {
        return ResponseCustom.OK(memberService.signUp(loginStatus.getMemberId(), postSignUpReq));
    }

//
//    @Operation(summary = "회원 정보 조회", description = "로그인된 회원의 정보를 반환합니다.", tags = {"Member Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
//    })
//    @GetMapping("/me")
//    public ResponseEntity<MemberResponse> findMe(@Parameter(hidden = true) @AuthenticationPrincipal LoginMember member) {
//        MemberResponse response = memberService.findById(member.getId());
//        return ResponseEntity.ok(response);
//    }
//
//    @Operation(summary = "산업 키워드 변경", description = "산업 키워드를 변경합니다.", tags = {"Member Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "204", description = "산업 키워드 변경 성공"),
//    })
//    @PutMapping("/me/industrykeywords")
//    public ResponseEntity<Void> updateIndustryKeywords(@RequestBody UpdateIndustryKeywordRequest request,
//                                                       @Parameter(hidden = true)  @AuthenticationPrincipal LoginMember member) {
//        memberService.updateIndustryKeywords(request, member.getId());
//        return ResponseEntity.noContent().build();
//    }
//
//    @Operation(summary = "키워드 변경", description = "키워드를 변경합니다.", tags = {"Member Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "204", description = "키워드 변경 성공"),
//    })
//    @PutMapping("/me/keywords")
//    public ResponseEntity<Void> updateKeyword(@RequestBody KeywordRequest request,
//                                              @Parameter(hidden = true) @AuthenticationPrincipal LoginMember member) {
//        memberService.updateKeyword(request, member.getId());
//        return ResponseEntity.noContent().build();
//    }
//
//    @Operation(summary = "키워드 삭제", description = "키워드를 삭제합니다.", tags = {"Member Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "204", description = "키워드 삭제 성공"),
//    })
//    @DeleteMapping("/me/keywords")
//    public ResponseEntity<Void> deleteKeyword(@RequestBody KeywordRequest request,
//                                              @Parameter(hidden = true) @AuthenticationPrincipal LoginMember member) {
//        memberService.deleteKeyword(request, member.getId());
//        return ResponseEntity.noContent().build();
//    }
}
