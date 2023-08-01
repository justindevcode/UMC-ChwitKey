package com.example.cherrypickserver.member.presentation;

import com.example.cherrypickserver.auth.dto.LoginMember;
import com.example.cherrypickserver.member.dto.MemberResponse;
import com.example.cherrypickserver.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member Controller", description = "회원 컨드롤러")
@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회", description = "로그인된 회원의 정보를 반환합니다.", tags = {"Member Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> findMe(@AuthenticationPrincipal LoginMember member) {
        MemberResponse response = memberService.findById(member.getId());
        return ResponseEntity.ok(response);
    }
}
