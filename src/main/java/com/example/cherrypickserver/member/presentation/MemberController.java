package com.example.cherrypickserver.member.presentation;

import com.example.cherrypickserver.global.dto.ResponseCustom;
import com.example.cherrypickserver.global.resolver.Auth;
import com.example.cherrypickserver.global.resolver.IsLogin;
import com.example.cherrypickserver.global.resolver.LoginStatus;
import com.example.cherrypickserver.member.application.KeywordService;
import com.example.cherrypickserver.member.application.MemberService;
import com.example.cherrypickserver.member.dto.request.PostKeywordReq;
import com.example.cherrypickserver.member.dto.request.PostSignUpReq;
import com.example.cherrypickserver.member.dto.request.SaveMemberReq;
import com.example.cherrypickserver.member.dto.request.UpdateIndustryReq;
import com.example.cherrypickserver.member.dto.request.UpdateNameReq;
import com.example.cherrypickserver.member.dto.response.LoginTokenRes;
import com.example.cherrypickserver.member.dto.response.MemberInfoRes;
import com.example.cherrypickserver.member.dto.response.MemberKeywordRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member Controller", description = "회원 컨드롤러")
@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final KeywordService keywordService;

    // 카카오 로그인 콜백
    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행한다." +
            "\n https://kauth.kakao.com/oauth/authorize?client_id={api-key}&redirect_uri={redirect-uri}&response_type=code 주소로 요청해서 카카오 로그인을 진행하면 해당 api로 redirect 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginTokenRes.class))}),
    })
    @ResponseBody
    @GetMapping("/callback/kakao")
    public ResponseCustom<LoginTokenRes> kakaoCallback(@RequestParam String code)
    {
        return ResponseCustom.OK(memberService.kakaoLogin(code));
    }

    // 첫 회원 프로필 설정
    @Operation(summary = "첫 회원 프로필 설정", description = "처음 회원가입시 프로필을 설정합니다." +
            " \n카카오 로그인을 진행했을 때 isMember 값이 false로 넘어오면 해당 api를 호출해주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 설정 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginTokenRes.class))}),
    })
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


    @Operation(summary = "이름 변경", description = "회원의 이름변경")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이름 변경 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    })
    @ResponseBody
    @PostMapping("/updateName")
    public ResponseCustom<String> updateName(
        @RequestBody UpdateNameReq updateNameReq
    )
    {
        return ResponseCustom.OK(memberService.updateName(updateNameReq));
    }

    @Operation(summary = "직군 변경", description = "직군 변경")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "직군 변경 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    })
    @ResponseBody
    @PostMapping("/updateIndustry")
    public ResponseCustom<String> updateIndustry(
        @RequestBody UpdateIndustryReq updateIndustryReq
    )
    {
        return ResponseCustom.OK(memberService.updateIndustryKeyword(updateIndustryReq));
    }

    @Operation(summary = "첫 회원 등록", description = "초기 회원 정보 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원 저장 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    })
    @ResponseBody
    @PostMapping("/save")
    public ResponseCustom<String> save(
        @RequestBody SaveMemberReq saveMemberReq
    )
    {
        return ResponseCustom.OK(memberService.save(saveMemberReq));
    }


    @Operation(summary = "유저 키워드 등록", description = "유저 키워드 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "키워드 저장 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    })
    @ResponseBody
    @PostMapping("/saveKeyword")
    public ResponseCustom<String> saveKeyword(
        @RequestBody PostKeywordReq postKeywordReq
    )
    {
        return ResponseCustom.OK(keywordService.save(postKeywordReq));
    }


    @Operation(summary = "유저 키워드 삭제", description = "키워드 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "키워드 삭제 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    })
    @ResponseBody
    @DeleteMapping("/deleteKeyword")
    public ResponseCustom<String> deleteKeyword(
        @RequestBody PostKeywordReq postKeywordReq
    )
    {
        return ResponseCustom.OK(keywordService.delete(postKeywordReq));
    }


    @Operation(summary = "유저 키워드 목록", description = "유저 키워드 목록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "키워드 목록 전송성공", content = {@Content(mediaType = "application/json",  schema = @Schema(implementation = MemberKeywordRes.class))}),
    })
    @ResponseBody
    @GetMapping("/keyword/{memberNumber}")
    public ResponseCustom<MemberKeywordRes> deleteKeyword(
        @PathVariable String memberNumber
    )
    {
        return ResponseCustom.OK(keywordService.getMemberKeyword(memberNumber));
    }

    @Operation(summary = "유저 정보", description = "유저 정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "키워드 정보 전송성공", content = {@Content(mediaType = "application/json",  schema = @Schema(implementation = MemberInfoRes.class))}),
    })
    @ResponseBody
    @GetMapping("/info/{memberNumber}")
    public ResponseCustom<MemberInfoRes> getMemberInfo(
        @PathVariable String memberNumber
    )
    {
        return ResponseCustom.OK(memberService.memberInfo(memberNumber));
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
