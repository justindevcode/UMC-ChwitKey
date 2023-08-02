package com.example.cherrypickserver.auth.presentation;

import com.example.cherrypickserver.auth.application.JwtProvider;
import com.example.cherrypickserver.auth.domain.CustomOAuth2User;
import com.example.cherrypickserver.member.dto.MemberResponse;
import com.example.cherrypickserver.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String FRONT_URL = "http://localhost:3000";
    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long id = oAuth2User.getId();
        MemberResponse member = memberService.findById(id);
        if (member.getBirthdate() == null) {
            String accessToken = jwtProvider.createAccessToken(String.valueOf(id));
            setHeadersWithTokens(response, accessToken);
            response.sendRedirect(FRONT_URL + "/login?token=" + BEARER + accessToken);
            return;
        }

        String accessToken = jwtProvider.createAccessToken(String.valueOf(id));
        setHeadersWithTokens(response, accessToken);
        response.sendRedirect(FRONT_URL);
    }

    private void setHeadersWithTokens(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, BEARER + accessToken);
    }
}
