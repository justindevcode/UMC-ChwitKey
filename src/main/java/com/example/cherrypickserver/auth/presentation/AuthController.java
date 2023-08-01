package com.example.cherrypickserver.auth.presentation;

import com.example.cherrypickserver.auth.application.JwtProvider;
import com.example.cherrypickserver.auth.dto.LoginMember;
import com.example.cherrypickserver.member.dto.MemberResponse;
import com.example.cherrypickserver.auth.dto.SignUpRequest;
import com.example.cherrypickserver.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "Auth Controller", description = "권한 컨드롤러")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Operation(summary = "회원가입", description = "추가 정보를 입력합니다.", tags = {"Auth Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
    })
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signUp(@AuthenticationPrincipal LoginMember loginMember,
                                                 @RequestBody SignUpRequest request) {
        MemberResponse response = memberService.signUp(loginMember.getId(), request);
        HttpHeaders headers = getHeadersWithTokens(loginMember.getId());
        return ResponseEntity.created(URI.create("/api/members/me")).headers(headers).body(response);
    }

    private HttpHeaders getHeadersWithTokens(Long id) {
        String accessToken = jwtProvider.createAccessToken(String.valueOf(id));

        HttpHeaders headers = new HttpHeaders();
        headers.set(accessHeader, accessToken);
        return headers;
    }

    @Operation(summary = "로그아웃", description = "로그아웃 합니다.", tags = {"Auth Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
    })
    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.noContent().build();
    }
}
